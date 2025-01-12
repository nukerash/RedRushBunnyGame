package net.s0ckett.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ListCommand extends RBCommandBase {
    public ListCommand(PlayerListManager playerListManager) {
        super(playerListManager);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!checkPermission(sender)) return true;

        Set<String> players = playerListManager.getPlayerList();
        if (players.isEmpty()) {
            sender.sendMessage(Component.text("Список пуст.").color(NamedTextColor.YELLOW));
        } else {
            sender.sendMessage(Component.text("Игроки в списке: " + String.join(", ", players)).color(NamedTextColor.AQUA));
        }
        return true;
    }
}