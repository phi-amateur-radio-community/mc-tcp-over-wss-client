package org.phiarc.mixin.client;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ManageServerScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.phiarc.gui.MtowConfigurationScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ManageServerScreen.class)
public class ManageServerScreenMixin extends Screen {

    protected ManageServerScreenMixin(Component component) {
        super(component);
    }

    @Shadow
    @Final
    protected BooleanConsumer callback;

    @Shadow
    protected EditBox ipEdit;

	@Inject(at = @At("TAIL"), method = "init")
	private void onInit(CallbackInfo info) {
        this.addRenderableWidget(Button.builder(Component.literal("WS"), (button) -> {
            assert this.minecraft != null;
            this.minecraft.setScreen(new MtowConfigurationScreen(this));
        }).bounds(this.width / 2 + 100 + 5, 106, 20, 20).build());
	}

}