package org.phiarc.gui;

import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

import java.util.HashMap;
import java.util.Map;

public enum MtowProtocol implements StringRepresentable {
    TCP(0, "tcp"),
    HTTP(1, "http");
    private final int id;
    private final String string;
    private static final Map<Integer, MtowProtocol> BY_ID = new HashMap<>();

    static {
        for (MtowProtocol value : values()) {
            BY_ID.put(value.id, value);
        }
    }

    private MtowProtocol(int id, String string) {
        this.id = id;
        this.string = string;
    }

    public int getId() {
        return id;
    }

    public static MtowProtocol create(int id) {
        return BY_ID.get(id);
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
