package org.phiarc.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MtowConfigurationManager {
    public MtowStatus proxyStatus = MtowStatus.OFF;
    public MtowProtocol protocolStatus = MtowProtocol.TCP;
    public List<String> proxyList = new ArrayList<>();
    public String password = "";
    public int activeAmount = 3;
    public int alternativeAmount = 5;
    public int reuseAmount = 2;
    public int resendIncrement = 1;
    public int activeInterval = 5;
    public int alternativeInterval = 30;

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
            builder.append(",")
                    .append(password).append(",")
                    .append(activeAmount).append(",")
                    .append(alternativeAmount).append(",")
                    .append(reuseAmount).append(",")
                    .append(resendIncrement).append(",")
                    .append(activeInterval).append(",")
                    .append(alternativeInterval);

            return builder.toString();
        }
        return uri;
    }

    public MtowConfigurationManager(String string) {
        if (string.startsWith(PREFIX)) {
            String[] contents = string.substring(PREFIX.length()).split(",", 10);
            try {
                this.proxyStatus = replace(MtowStatus.create(limit(Integer.parseInt(contents[0]), 2)), this.proxyStatus);
                this.protocolStatus = replace(MtowProtocol.create(limit(Integer.parseInt(contents[1]), 1)), this.protocolStatus);
                this.proxyList = replace(Arrays.asList(contents[2].split(";")), this.proxyList);
                this.password = replace(contents[3], this.password);
                this.activeAmount = replace(Math.max(Integer.parseInt(contents[4]), 0), this.activeAmount);
                this.alternativeAmount = replace(Math.max(Integer.parseInt(contents[5]), 0), this.alternativeAmount);
                this.reuseAmount = replace(Math.max(Integer.parseInt(contents[6]), 0), this.reuseAmount);
                this.resendIncrement = replace(Math.max(Integer.parseInt(contents[7]), 0), this.resendIncrement);
                this.activeInterval = replace(Math.max(Integer.parseInt(contents[8]), 0), this.activeInterval);
                this.alternativeInterval = replace(Math.max(Integer.parseInt(contents[9]), 0), this.alternativeInterval);
            } catch (Exception ignored) {}
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
