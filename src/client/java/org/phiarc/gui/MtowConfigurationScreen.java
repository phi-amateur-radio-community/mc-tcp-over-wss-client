package org.phiarc.gui;

import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class MtowConfigurationScreen extends Screen {
    private final Screen lastScreen;

    private final static Component TITLE = Component.translatable("mtow.screen.configure_title");
    private final static Component PASSWD_TITLE = Component.translatable("mtow.screen.password_title");
    private final static Component SERVER_BOTTON = Component.translatable("mtow.screen.server_botton");
    private final static Component RULES_BOTTON = Component.translatable("mtow.screen.rule_botton");

    private CycleButton<MtowStatus> statusButton;
    private CycleButton<MtowProtocol> protocolButton;
    private Button serverButton;
    private Button strategyButton;
    private EditBox passwdBox;
    private HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 61, 33);

    public MtowConfigurationScreen(Screen screen) {
        super(TITLE);
        lastScreen = screen;
    }

    protected void init() {
        this.layout.addTitleHeader(TITLE, this.font);

        LinearLayout linearLayout = ((LinearLayout)this.layout.addToHeader(LinearLayout.vertical())).spacing(4);
        statusButton = CycleButton.builder(MtowStatus::getDisplayName).withValues(MtowStatus.values()).withInitialValue(MtowStatus.OFF).create(0, 0, 200, 20, MtowStatus.getLabel(), ((cycleButton, object) -> updateStates()));
        linearLayout.addChild(statusButton);
        serverButton = Button.builder(SERVER_BOTTON, (button) -> {
            assert this.minecraft != null;
            this.minecraft.setScreen(new MtowServerScreen(this));
        }).bounds(0, 0, 200, 20).build();
        linearLayout.addChild(serverButton);
        strategyButton = Button.builder(RULES_BOTTON, (button) -> {
            assert this.minecraft != null;
            this.minecraft.setScreen(new MtowProxyRulesScreen(this));
        }).bounds(0, 0, 200, 20).build();
        linearLayout.addChild(strategyButton);
        protocolButton = CycleButton.builder(MtowProtocol::getDisplayName).withValues(MtowProtocol.values()).withInitialValue(MtowProtocol.TCP).create(0, 0, 200, 20, MtowProtocol.getLabel(), ((cycleButton, object) -> System.out.println("111")));
        linearLayout.addChild(protocolButton);
        passwdBox = new EditBox(this.font, 200, 20, PASSWD_TITLE);
        passwdBox.setHint(PASSWD_TITLE);
        linearLayout.addChild(passwdBox);
        this.layout.addToContents(linearLayout);

        this.layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, (buttonx) -> this.onClose()).width(200).build());
        this.layout.visitWidgets((guiEventListener) -> {
            AbstractWidget var10000 = (AbstractWidget)this.addRenderableWidget(guiEventListener);
        });
        this.repositionElements();
        updateStates();
    }

    protected void repositionElements() {
        this.layout.arrangeElements();
    }

    protected void updateStates() {
        boolean configureStatus = statusButton.getValue().mtowConfigureEnable();
        boolean serverListStatus = statusButton.getValue().serverListEnable();
        protocolButton.active = configureStatus;
        passwdBox.active = configureStatus;
        serverButton.active = serverListStatus;
        strategyButton.active = serverListStatus;
    }

    public void onClose() {
        assert this.minecraft != null;
        this.minecraft.setScreen(this.lastScreen);
    }
}
