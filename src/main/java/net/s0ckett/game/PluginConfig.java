package net.s0ckett.game;

import org.bukkit.configuration.file.FileConfiguration;

public class PluginConfig {
    private final FileConfiguration config;

    public PluginConfig(FileConfiguration config) {
        this.config = config;
    }

    public int getBorderSpeed() {
        return config.getInt("border-speed", 1);
    }

    public boolean isSpectatorOnDeath() {
        return config.getBoolean("spectator-on-death", false);
    }

    public void setSpectatorOnDeath(boolean value) {
        config.set("spectator-on-death", value);
    }

    public int getDefaultBorderSize() {
        return config.getInt("default-border-size", 1000);
    }

    public int getGlowingDuration() {
        return config.getInt("glowing-duration", 60);
    }

    public int getGatewayRemoveDelay() {
        return config.getInt("gateway-remove-delay", 60); // Новый метод
    }
}