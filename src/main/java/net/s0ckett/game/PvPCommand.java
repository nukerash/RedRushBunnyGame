package net.s0ckett.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class PvPCommand extends RBCommandBase {
    public PvPCommand(PlayerListManager playerListManager) {
        super(playerListManager);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!checkPermission(sender)) return true;

        if (args.length < 1) {
            sender.sendMessage(Component.text("Используйте: /rb pvp <on|off>").color(NamedTextColor.RED));
            return true;
        }

        boolean enablePvP = args[0].equalsIgnoreCase("on");

        // Устанавливаем PvP для всех игроков
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setInvulnerable(!enablePvP); // Если PvP выключен, игроки неуязвимы
        }

        String message = enablePvP ? "PvP включен для всех игроков." : "PvP отключен для всех игроков.";
        sender.sendMessage(Component.text(message).color(NamedTextColor.GREEN));

        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("on", "off"); // Предлагаем "on" и "off" для автодополнения
        }
        return Collections.emptyList();
    }
}