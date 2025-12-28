package org.phiarc.gui;

import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

public enum MtowStatus implements StringRepresentable {
    OFF(false, "off"),
    MIX(true, "mix"),
    CLIENT(true, "client")
    ;
    public final boolean enable;
    private final String string;

    private MtowStatus(boolean enable, String string) {
        this.enable = enable;
        this.string = string;
    }

    public Component getDisplayName() {
        return Component.translatable("mtow.status." + this.string);
    }

    public static Component getLabel() {
        return Component.translatable("mtow.status.label");
    }

    @Override
    public String getSerializedName() {
        return string;
    }
}
