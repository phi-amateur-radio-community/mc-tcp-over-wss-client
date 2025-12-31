package org.phiarc.gui.screen;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.phiarc.gui.MtowSelectionList;

public class MtowServerScreen extends Screen {
    private final MtowConfigurationScreen lastScreen;

    private final static Component TITLE = Component.translatable("mtow.screen.server_title");

    protected MtowSelectionList proxyServerList;
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 61, 33);

    protected MtowServerScreen(MtowConfigurationScreen screen) {
        super(TITLE);
        lastScreen = screen;
    }

    protected void init() {
        this.layout.addTitleHeader(TITLE, this.font);
        this.proxyServerList = (MtowSelectionList) this.layout.addToContents(new MtowSelectionList(this, this.minecraft, this.width, this.layout.getContentHeight(), this.layout.getHeaderHeight(), 18));
        this.proxyServerList.initProxyList(lastScreen.configuration.proxyList);

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
        this.lastScreen.configuration.proxyList = this.proxyServerList.getProxyList();
        assert this.minecraft != null;
        this.minecraft.setScreen(this.lastScreen);
    }
}
