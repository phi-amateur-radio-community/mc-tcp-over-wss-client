package org.phiarc.gui;

import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

public enum MtowProtocol implements StringRepresentable {
    TCP(0, "tcp"),
    HTTP(1, "http");
    private final int id;
    private final String string;

    private MtowProtocol(int id, String string) {
        this.id = id;
        this.string = string;
    }

    public int getId() {
        return id;
    }

    public Component getDisplayName() {
        return Component.translatable("mtow.protocol." + this.string);
    }

    public static Component getLabel() {
        return Component.translatable("mtow.protocol.label");
    }

    @Override
    public String getSerializedName() {
        return string;
    }
}
