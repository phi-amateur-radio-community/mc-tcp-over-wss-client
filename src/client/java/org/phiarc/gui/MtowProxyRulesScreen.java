package org.phiarc.gui;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MtowProxyRulesScreen extends Screen {
    private final Screen lastScreen;

    private final static Component TITLE = Component.translatable("mtow.screen.rule_title");

    private HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 61, 33);

    protected MtowProxyRulesScreen(Screen screen) {
        super(TITLE);
        lastScreen = screen;
    }

    public void init() {
        this.layout.addTitleHeader(TITLE, this.font);

        this.layout.visitWidgets((guiEventListener) -> {
            AbstractWidget var10000 = (AbstractWidget)this.addRenderableWidget(guiEventListener);
        });
        this.repositionElements();
    }

    protected void repositionElements() {
        this.layout.arrangeElements();
    }

    public void onClose() {
        assert this.minecraft != null;
        this.minecraft.setScreen(this.lastScreen);
    }
}
