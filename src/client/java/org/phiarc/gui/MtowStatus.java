package org.phiarc.gui;

import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

import java.util.HashMap;
import java.util.Map;

public enum MtowStatus implements StringRepresentable {
    OFF(0, "off"),
    MIX(1, "remote"),
    CLIENT(2, "local")
    ;
    private final int id;
    private final String string;
    private static final Map<Integer, MtowStatus> BY_ID = new HashMap<>();

    static {
        for (MtowStatus value : values()) {
            BY_ID.put(value.id, value);
        }
    }

    private MtowStatus(int id, String string) {
        this.id = id;
        this.string = string;
    }

    public static MtowStatus create(int id) {
        return BY_ID.get(id);
    }

    public int getId() {
        return id;
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
