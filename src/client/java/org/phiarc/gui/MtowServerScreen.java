package org.phiarc.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LayoutSettings;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;

public class MtowServerScreen extends Screen {
    private final Screen lastScreen;

    private final static Component TITLE = Component.translatable("mtow.screen.server_title");

    protected MtowSelectionList proxyServerList;
    private HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 61, 33);

    protected MtowServerScreen(Screen screen) {
        super(TITLE);
        lastScreen = screen;
    }

    protected void init() {
        this.layout.addTitleHeader(TITLE, this.font);
        this.proxyServerList = (MtowSelectionList) this.layout.addToContents(new MtowSelectionList(this, this.minecraft, this.width, this.layout.getContentHeight(), this.layout.getHeaderHeight(), 18));
        this.proxyServerList.initProxyList(new ArrayList<>());

        this.layout.visitWidgets((guiEventListener) -> {
            AbstractWidget var10000 = (AbstractWidget)this.addRenderableWidget(guiEventListener);
        });
        this.repositionElements();
    }

    protected void repositionElements() {
        this.layout.arrangeElements();
        if (this.proxyServerList != null) {
            this.proxyServerList.updateSize(this.width, this.layout);
        }
    }

    public void onClose() {
        this.proxyServerList.onClose();
        assert this.minecraft != null;
        this.minecraft.setScreen(this.lastScreen);
    }
}
