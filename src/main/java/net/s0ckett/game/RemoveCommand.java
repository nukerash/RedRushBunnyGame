package net.s0ckett.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RemoveCommand extends RBCommandBase {
    public RemoveCommand(PlayerListManager playerListManager) {
        super(playerListManager);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!checkPermission(sender)) return true;

        if (args.length < 1) {
            sender.sendMessage(Component.text("Укажите имя игрока.").color(NamedTextColor.RED));
            return true;
        }

        String playerName = args[0]; // args[0] — это имя игрока
        playerListManager.removePlayer(playerName);
        sender.sendMessage(Component.text("Игрок " + playerName + " удален из списка.").color(NamedTextColor.GREEN));
        return true;
    }
}