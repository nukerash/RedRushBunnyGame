package net.s0ckett.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AddCommand extends RBCommandBase {
    public AddCommand(PlayerListManager playerListManager) {
        super(playerListManager);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!checkPermission(sender)) return true;

        if (args.length < 1) {
            sender.sendMessage(Component.text("Укажите имя игрока.").color(NamedTextColor.RED));
            return true;
        }

        String playerName = args[0];
        playerListManager.addPlayer(playerName);
        sender.sendMessage(Component.text("Игрок " + playerName + " добавлен в список.").color(NamedTextColor.GREEN));
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 1) {
            // Возвращаем список ников онлайн-игроков
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName) // Получаем ники игроков
                    .filter(name -> name.startsWith(args[0])) // Фильтруем по введенному тексту
                    .collect(Collectors.toList());
        }
        return Collections.emptyList(); // Если аргументов больше одного, возвращаем пустой список
    }
}