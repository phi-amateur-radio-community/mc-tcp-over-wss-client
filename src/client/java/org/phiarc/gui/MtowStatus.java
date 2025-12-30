package org.phiarc.gui;

import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

public enum MtowStatus implements StringRepresentable {
    OFF(0, "off"),
    MIX(1, "remote"),
    CLIENT(2, "local")
    ;
    private final int id;
    private final String string;

    private MtowStatus(int id, String string) {
        this.id = id;
        this.string = string;
    }

    public boolean serverListEnable() {
        return id == 2;
    }

    public boolean mtowConfigureEnable() {
        return id != 0;
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
