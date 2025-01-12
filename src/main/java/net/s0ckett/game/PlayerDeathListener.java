package net.s0ckett.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    private final Main plugin;
    private final PluginConfig pluginConfig;

    /**
     * Конструктор класса.
     *
     * @param plugin       Основной класс плагина.
     * @param pluginConfig Конфигурация плагина.
     */
    public PlayerDeathListener(Main plugin, PluginConfig pluginConfig) {
        this.plugin = plugin;
        this.pluginConfig = pluginConfig;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        // Проверяем, включен ли режим наблюдателя после смерти
        if (pluginConfig.isSpectatorOnDeath()) {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(Component.text("Вы переведены в режим наблюдателя после смерти.").color(NamedTextColor.GRAY));
        }
    }
}