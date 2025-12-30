package org.phiarc.gui;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MtowProxyRulesScreen extends Screen {
    private final Screen lastScreen;

    private final static Component TITLE = Component.translatable("mtow.screen.rule_title");

    protected MtowProxyRulesScreen(Screen screen) {
        super(TITLE);
        lastScreen = screen;
    }

    public void onClose() {
        assert this.minecraft != null;
        this.minecraft.setScreen(this.lastScreen);
    }
}
