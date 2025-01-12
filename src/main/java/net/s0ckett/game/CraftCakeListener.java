package net.s0ckett.game;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;

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

        // Проверяем, что игрок скрафтил торт и находится в списке
        if (result.getType() == Material.CAKE && playerListManager.containsPlayer(player.getName())) {
            // Вызываем команду /rb stop
            Bukkit.dispatchCommand(player, "rb stop");
        }
    }
}