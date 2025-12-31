package org.phiarc.gui;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.phiarc.gui.screen.MtowServerScreen;

import java.util.ArrayList;
import java.util.List;

public class MtowSelectionList extends ObjectSelectionList<MtowSelectionList.Entry> {
    private final MtowServerScreen screen;
    private ArrayList<String> proxyList;
    private ProxyServerEntry editingEntry;

    public MtowSelectionList(MtowServerScreen screen, Minecraft minecraft, int i, int j, int k, int l) {
        super(minecraft, i, j, k, l);
        this.screen = screen;
    }

    public int getRowWidth() {
        return 300;
    }

    public void initProxyList(List<String> proxyList) {
        for (String proxyServer : proxyList) {
            if (!proxyServer.isEmpty()) {
                this.addEntry(new ProxyServerEntry(minecraft, proxyServer, this));
            }
        }
        this.proxyList = new ArrayList<>(proxyList);
        this.addEntry(new AddServerEntry(this.minecraft, this));
    }

    public List<String> getProxyList() {
        return this.proxyList;
    }

    public void onClose() {
        if (editingEntry != null) {
            editingEntry.end();
        }
    }

    @Override
    public void updateSize(int i, HeaderAndFooterLayout headerAndFooterLayout) {
        if (editingEntry != null) {
            editingEntry.end();
        }
        super.updateSize(i, headerAndFooterLayout);
    }

    public abstract static class Entry extends ObjectSelectionList.Entry<Entry> {
        protected final MtowSelectionList list;

        public Entry(MtowSelectionList list) {
            this.list = list;
        }

        public void edit() {
            if (this.list.editingEntry != null) {
                list.editingEntry.end();
            }
        }


    }

    public static class ProxyServerEntry extends Entry {
        protected final Minecraft minecraft;
        private String uri;
        private final EditBox editBox;
        private static final long DOUBLE_CLICK_DELAY = 250;
        private long lastClick = 0;

        public ProxyServerEntry(Minecraft minecraft, String uri, MtowSelectionList list) {
            super(list);
            this.minecraft = minecraft;
            this.uri = uri;
            editBox = new EditBox(this.minecraft.font, 0, 0, 0, 0, Component.empty());
            editBox.setVisible(false);
        }

        private void end() {
            this.editBox.setFocused(false);
            this.uri = this.editBox.getValue();
            if (this.editBox.getValue().isEmpty()) {
                this.list.proxyList.remove(this.uri);
                this.list.removeEntry(this);
            } else {
                this.editBox.setVisible(false);
                this.list.proxyList.add(this.uri);
            }
        }

        @Override
        public void edit() {
            super.edit();
            this.list.proxyList.remove(this.uri);
            list.editingEntry = this;
            this.editBox.setVisible(true);
            this.editBox.setFocused(true);
        }

        @Override
        public Component getNarration() {
            return Component.empty();
        }

        @Override
        public void renderContent(GuiGraphics guiGraphics, int i, int j, boolean bl, float f) {
            this.editBox.setPosition(this.getX(), this.getY());
            this.editBox.setSize(this.getWidth(), this.getHeight());
            if (this.editBox.visible) {
                this.editBox.render(guiGraphics, (int)this.minecraft.mouseHandler.xpos(), (int)this.minecraft.mouseHandler.ypos(), bl?-1:-8355712);
            } else {
                guiGraphics.drawCenteredString(minecraft.font, uri, this.getX() + 150, this.getY() + 6, bl?-1:-8355712);
            }
        }

        @Override
        public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean bl) {
            if (this.list.editingEntry != this) {
                long now = Util.getMillis();
                if (now - lastClick < DOUBLE_CLICK_DELAY) {
                    this.edit();
                    return this.mouseClicked(mouseButtonEvent, bl);
                }
                lastClick = now;
                return false;
            }
            return this.editBox.mouseClicked(mouseButtonEvent, bl);
        }

        @Override
        public boolean keyPressed(KeyEvent keyEvent) {
            return editBox.keyPressed(keyEvent);
        }

        @Override
        public boolean charTyped(CharacterEvent characterEvent) {
            return editBox.charTyped(characterEvent);
        }
    }

    public static class AddServerEntry extends Entry {
        protected final Minecraft minecraft;
        private final static Component ADD_PROXY_SERVER = Component.translatable("mtow.screen.proxy_add");

        private AddServerEntry(Minecraft minecraft, MtowSelectionList list) {
            super(list);
            this.minecraft = minecraft;
        }

        public void edit() {
            ProxyServerEntry entry = new ProxyServerEntry(minecraft, "", list);
            this.list.addEntryToTop(entry);
            entry.edit();
        }

        @Override
        public Component getNarration() {
            return Component.empty();
        }

        @Override
        public void renderContent(GuiGraphics guiGraphics, int i, int j, boolean bl, float f) {
            guiGraphics.drawCenteredString(minecraft.font, ADD_PROXY_SERVER, this.getX() + 150, this.getY() + 6, bl?-1:-8355712);
        }

        @Override
        public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean bl) {
            this.edit();
            this.setFocused(false);
            return true;
        }

        @Override
        public boolean keyPressed(KeyEvent keyEvent) {
            if (this.list.editingEntry != null) {
                return this.list.editingEntry.editBox.keyPressed(keyEvent);
            }
            return false;
        }

        @Override
        public boolean charTyped(CharacterEvent characterEvent) {
            if (this.list.editingEntry != null) {
                return this.list.editingEntry.editBox.charTyped(characterEvent);
            }
            return false;
        }
    }
}
