package org.phiarc.gui.screen;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MtowProxyRulesScreen extends Screen {
    private final MtowConfigurationScreen lastScreen;

    private final static Component TITLE = Component.translatable("mtow.screen.rule_title");
    private final static Component ACTIVE_AMOUNT = Component.translatable("mtow.network.active_amount");
    private final static Component ALTERNATIVE_AMOUNT = Component.translatable("mtow.network.alternative_amount");
    private final static Component REUSE_AMOUNT = Component.translatable("mtow.network.reuse_amount");
    private final static Component RESEND_INCREMENT = Component.translatable("mtow.network.resend_increment");
    private final static Component ACTIVE_INTERVAL = Component.translatable("mtow.network.active_interval");
    private final static Component ALTERNATIVE_INTERVAL = Component.translatable("mtow.network.alternative_interval");

    private EditBox activeAmountEdit;
    private EditBox alternativeAmountEdit;
    private EditBox reuseAmountEdit;
    private EditBox resendIncrementEdit;
    private EditBox activeIntervalEdit;
    private EditBox alternativeIntervalEdit;

    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 61, 33);

    protected MtowProxyRulesScreen(MtowConfigurationScreen screen) {
        super(TITLE);
        lastScreen = screen;
    }

    /*
        activeAmountEdit.;
        alternativeAmountEdit.;
        reuseAmountEdit.;
        resendIncrementEdit.;
        activeIntervalEdit.;
        alternativeIntervalEdit.;
     */

    public void init() {
        this.layout.addTitleHeader(TITLE, this.font);

        activeAmountEdit = new EditBox(this.font, 100, 20, ACTIVE_AMOUNT);
        alternativeAmountEdit = new EditBox(this.font, 100, 20, ALTERNATIVE_AMOUNT);
        reuseAmountEdit = new EditBox(this.font, 100, 20, REUSE_AMOUNT);
        resendIncrementEdit = new EditBox(this.font, 100, 20, RESEND_INCREMENT);
        activeIntervalEdit = new EditBox(this.font, 100, 20, ACTIVE_INTERVAL);
        alternativeIntervalEdit = new EditBox(this.font, 100, 20, ALTERNATIVE_INTERVAL);
        activeAmountEdit.setHint(ACTIVE_AMOUNT);
        alternativeAmountEdit.setHint(ALTERNATIVE_AMOUNT);
        reuseAmountEdit.setHint(REUSE_AMOUNT);
        resendIncrementEdit.setHint(RESEND_INCREMENT);
        activeIntervalEdit.setHint(ACTIVE_INTERVAL);
        alternativeIntervalEdit.setHint(ALTERNATIVE_INTERVAL);
        /*
        activeAmountEdit.setValue(String.valueOf(this.lastScreen.configuration.activeAmount));
        alternativeAmountEdit.setValue(String.valueOf(this.lastScreen.configuration.alternativeAmount));
        reuseAmountEdit.setValue(String.valueOf(this.lastScreen.configuration.reuseAmount));
        resendIncrementEdit.setValue(String.valueOf(this.lastScreen.configuration.resendIncrement));
        activeIntervalEdit.setValue(String.valueOf(this.lastScreen.configuration.activeInterval));
        alternativeIntervalEdit.setValue(String.valueOf(this.lastScreen.configuration.alternativeInterval));
         */
        LinearLayout linearLayout1 = ((LinearLayout)this.layout.addToHeader(LinearLayout.vertical())).spacing(4);
        LinearLayout linearLayout2 = ((LinearLayout)this.layout.addToHeader(LinearLayout.vertical())).spacing(4);
        linearLayout1.addChild(activeAmountEdit);
        linearLayout2.addChild(alternativeAmountEdit);
        linearLayout1.addChild(reuseAmountEdit);
        linearLayout2.addChild(resendIncrementEdit);
        linearLayout1.addChild(activeIntervalEdit);
        linearLayout2.addChild(alternativeIntervalEdit);
        LinearLayout linearLayout = ((LinearLayout)this.layout.addToHeader(LinearLayout.horizontal())).spacing(4);
        linearLayout.addChild(linearLayout1);
        linearLayout.addChild(linearLayout2);

        this.layout.addToContents(linearLayout);

        this.layout.visitWidgets((guiEventListener) -> {
            AbstractWidget var10000 = (AbstractWidget)this.addRenderableWidget(guiEventListener);
        });
        this.repositionElements();
    }

    protected void repositionElements() {
        this.layout.arrangeElements();
    }

    public void onClose() {
        try {
            this.lastScreen.configuration.activeAmount = Integer.parseInt(activeAmountEdit.getValue());
            this.lastScreen.configuration.alternativeAmount = Integer.parseInt(alternativeAmountEdit.getValue());
            this.lastScreen.configuration.reuseAmount = Integer.parseInt(reuseAmountEdit.getValue());
            this.lastScreen.configuration.resendIncrement = Integer.parseInt(resendIncrementEdit.getValue());
            this.lastScreen.configuration.activeInterval = Integer.parseInt(activeIntervalEdit.getValue());
            this.lastScreen.configuration.alternativeInterval = Integer.parseInt(alternativeIntervalEdit.getValue());
        } catch (Exception ignored) {}
        assert this.minecraft != null;
        this.minecraft.setScreen(this.lastScreen);
    }
}
