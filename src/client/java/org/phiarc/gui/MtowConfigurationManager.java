package org.phiarc.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MtowConfigurationManager {
    public MtowStatus proxyStatus = MtowStatus.OFF;
    public MtowProtocol protocolStatus = MtowProtocol.TCP;
    public List<String> proxyList = new ArrayList<>();
    public String password = "";


    private final String PREFIX = "@mtow::";

    public String serialization(String uri) {
        if (proxyStatus != MtowStatus.OFF) {
            StringBuilder builder = new StringBuilder(PREFIX);
            builder
                    .append(proxyStatus.getId()).append(",")
                    .append(protocolStatus.getId()).append(",");
            if (!proxyList.isEmpty()) {
                proxyList.forEach(string -> builder.append(string).append(";"));
                builder.setLength(builder.length() - 1);
            }
            builder.append(",").append(password);

            return builder.toString();
        }
        return uri;
    }

    private MtowConfigurationManager() {}

    public MtowConfigurationManager copy() {
        MtowConfigurationManager manager = new MtowConfigurationManager();
        manager.proxyStatus = this.proxyStatus;
        manager.protocolStatus = this.protocolStatus;
        manager.proxyList = new ArrayList<>(this.proxyList);
        manager.password = this.password;
        return manager;
    }

    public MtowConfigurationManager(String string) {
        if (string.startsWith(PREFIX)) {
            String[] contents = string.substring(PREFIX.length()).split(",", 4);
            try {
                this.proxyStatus = replace(MtowStatus.create(limit(Integer.parseInt(contents[0]), 2)), this.proxyStatus);
                this.protocolStatus = replace(MtowProtocol.create(limit(Integer.parseInt(contents[1]), 1)), this.protocolStatus);
                this.proxyList = replace(Arrays.asList(contents[2].split(";")), this.proxyList);
                this.password = replace(contents[3], this.password);
            } catch (Exception ignored) {
            }
        }
    }

    private int limit(int value, int max) {
        if (value < 0) {
            return 0;
        }
        return Math.min(value, max);
    }

    private static <T> T replace(T object1, T object2) {
        return object1 == null ? object2 : object1;
    }
}
