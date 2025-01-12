package net.s0ckett.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class TeleportAllCommand extends RBCommandBase {
    public TeleportAllCommand(PlayerListManager playerListManager) {
        super(playerListManager);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!checkPermission(sender)) return true;

        Player targetPlayer;
        if (args.length > 0) {
            // Если указан аргумент (например, /rb tpall nukerash)
            targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) {
                sender.sendMessage(Component.text("Игрок " + args[0] + " не найден или не в сети.").color(NamedTextColor.RED));
                return true;
            }
        } else {
            // Если аргумент не указан (например, /rb tpall)
            if (!(sender instanceof Player)) {
                sender.sendMessage(Component.text("Эту команду может выполнить только игрок, если не указан целевой игрок.").color(NamedTextColor.RED));
                return true;
            }
            targetPlayer = (Player) sender;
        }

        Location targetLocation = targetPlayer.getLocation();
        Set<String> players = playerListManager.getPlayerList();

        if (players.isEmpty()) {
            sender.sendMessage(Component.text("Нет игроков в списке для телепортации.").color(NamedTextColor.YELLOW));
            return true;
        }

        for (String playerName : players) {
            Player player = Bukkit.getPlayer(playerName);
            if (player != null && player.isOnline()) {
                player.teleport(targetLocation);
                player.sendMessage(Component.text("Вы были телепортированы к " + targetPlayer.getName() + ".").color(NamedTextColor.GREEN));
            }
        }

        sender.sendMessage(Component.text("Все игроки из списка телепортированы к " + targetPlayer.getName() + ".").color(NamedTextColor.GREEN));
        return true;
    }
}