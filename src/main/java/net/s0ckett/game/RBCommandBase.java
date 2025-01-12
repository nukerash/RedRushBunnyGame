package net.s0ckett.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public abstract class RBCommandBase {
    protected final PlayerListManager playerListManager;

    public RBCommandBase(PlayerListManager playerListManager) {
        this.playerListManager = playerListManager;
    }

    public abstract boolean execute(@NotNull CommandSender sender, @NotNull String[] args);

    protected boolean checkPermission(CommandSender sender) {
        if (!sender.hasPermission("nuke.sosihuy")) {
            sender.sendMessage(Component.text("У вас нет разрешения для использования этой команды.").color(NamedTextColor.RED));
            return false;
        }
        return true;
    }

    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList(); // По умолчанию возвращаем пустой список
    }
}