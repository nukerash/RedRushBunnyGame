package net.s0ckett.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.format.NamedTextColor;

import java.time.Duration;

public class GameEndHandler {
    private final PlayerListManager playerListManager;
    private final Main plugin;

    public GameEndHandler(PlayerListManager playerListManager, Main plugin) {
        this.playerListManager = playerListManager;
        this.plugin = plugin;
    }

    public void endGame(Player player) {
        // Получаем спавн из конфигурации
        Location spawnLocation = (Location) plugin.getConfig().get("spawn-location");
        if (spawnLocation == null) {
            player.sendMessage(Component.text("Спавн не установлен! Используйте /setspawn.").color(NamedTextColor.RED));
            plugin.getLogger().warning("Спавн не установлен!");
            return;
        }

        plugin.getLogger().info("Спавн загружен: " + spawnLocation);

        // Телепортируем всех онлайн-игроков на спавн и очищаем их инвентари
        for (Player targetPlayer : Bukkit.getOnlinePlayers()) {
            plugin.getLogger().info("Телепортируем игрока: " + targetPlayer.getName());
            targetPlayer.teleport(spawnLocation);
            targetPlayer.getInventory().clear();
        }

        // Показываем title и subtitle всем игрокам
        Title title = Title.title(
                Component.text(player.getName() + " скрафтил торт!").color(NamedTextColor.RED),
                Component.text("Игра окончена!").color(NamedTextColor.WHITE),
                Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(5), Duration.ofSeconds(1))
        );
        Bukkit.getOnlinePlayers().forEach(p -> p.showTitle(title));

        // Устанавливаем границу мира
        WorldBorder worldBorder = Bukkit.getWorlds().get(0).getWorldBorder();
        int borderSize = plugin.getPluginConfig().getDefaultBorderSize();
        worldBorder.setSize(borderSize);
        worldBorder.setCenter(spawnLocation);
    }
}