package org.phiarc.gui;

import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LayoutSettings;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class MtowConfigurationScreen extends Screen {
    private final Screen lastScreen;

    private final static Component TITLE = Component.translatable("mtow.screen.configure_title");
    private final static Component PASSWD_TITLE = Component.translatable("mtow.screen.password_title");
    private final static Component SERVER_BOTTON = Component.translatable("mtow.screen.server_botton");

    private CycleButton<MtowStatus> statusButton;
    private CycleButton<MtowProtocol> protocolButton;
    private Button serverButton;
    private EditBox passwdBox;
    private HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 61, 33);

    public MtowConfigurationScreen(Screen screen) {
        super(TITLE);
        lastScreen = screen;
    }

    protected void init() {
        LinearLayout linearLayout = ((LinearLayout)this.layout.addToHeader(LinearLayout.vertical())).spacing(8);
        linearLayout.addChild(new StringWidget(TITLE, this.font), LayoutSettings::alignHorizontallyCenter);

        LinearLayout linearLayout1 = ((LinearLayout)this.layout.addToHeader(LinearLayout.vertical())).spacing(8);
        statusButton = CycleButton.builder(MtowStatus::getDisplayName).withValues(MtowStatus.values()).withInitialValue(MtowStatus.OFF).create(0, 0, 200, 20, MtowStatus.getLabel(), ((cycleButton, object) -> updateStates()));
        linearLayout1.addChild(statusButton);
        serverButton = Button.builder(SERVER_BOTTON, (button) -> {
            assert this.minecraft != null;
            this.minecraft.setScreen(new MtowServerScreen(this));
        }).bounds(0, 0, 200, 20).build();
        linearLayout1.addChild(serverButton);
        protocolButton = CycleButton.builder(MtowProtocol::getDisplayName).withValues(MtowProtocol.values()).withInitialValue(MtowProtocol.TCP).create(0, 0, 200, 20, MtowProtocol.getLabel(), ((cycleButton, object) -> System.out.println("111")));
        linearLayout1.addChild(protocolButton);
        passwdBox = new EditBox(this.font, 200, 20, PASSWD_TITLE);
        passwdBox.setHint(PASSWD_TITLE);
        linearLayout1.addChild(passwdBox);
        this.layout.addToContents(linearLayout1);

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
        boolean status = statusButton.getValue().enable;
        protocolButton.active = status;
        passwdBox.active = status;
        serverButton.active = status;
    }

    public void onClose() {
        assert this.minecraft != null;
        this.minecraft.setScreen(this.lastScreen);
    }
}
