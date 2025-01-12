package net.s0ckett.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;

public class CraftCakeListener implements Listener {
    private final PlayerListManager playerListManager;
    private final Main plugin;

    public CraftCakeListener(PlayerListManager playerListManager, Main plugin) {
        this.playerListManager = playerListManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack result = event.getRecipe().getResult();

        // Проверяем, что игрок скрафтил торт
        if (result.getType() == Material.CAKE && playerListManager.containsPlayer(player.getName())) {
            // Получаем спавн из конфигурации
            Location spawnLocation = (Location) plugin.getConfig().get("spawn-location");
            if (spawnLocation == null) {
                player.sendMessage(Component.text("Спавн не установлен! Используйте /setspawn.").color(NamedTextColor.RED));
                return;
            }

            // Телепортируем всех игроков на спавн и очищаем их инвентари
            for (String playerName : playerListManager.getPlayerList()) {
                Player targetPlayer = Bukkit.getPlayer(playerName);
                if (targetPlayer != null && targetPlayer.isOnline()) {
                    targetPlayer.teleport(spawnLocation);
                    targetPlayer.getInventory().clear();
                }
            }

            // Показываем title и subtitle
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
}