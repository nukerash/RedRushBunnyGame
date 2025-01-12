package net.s0ckett.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetSpawnCommand extends RBCommandBase {
    private final Main plugin;

    public SetSpawnCommand(PlayerListManager playerListManager, Main plugin) {
        super(playerListManager);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Component.text("Эту команду может выполнить только игрок.").color(NamedTextColor.RED));
            return true;
        }

        Player player = (Player) sender;
        Location spawnLocation = player.getLocation();

        // Сохраняем координаты спавна в конфигурацию
        plugin.getConfig().set("spawn-location", spawnLocation);
        plugin.saveConfig();

        player.sendMessage(Component.text("Спавн установлен на ваши текущие координаты.").color(NamedTextColor.GREEN));
        return true;
    }
}