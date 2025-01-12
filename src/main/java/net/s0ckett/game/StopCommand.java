package net.s0ckett.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class StopCommand extends RBCommandBase {
    private final GameEndHandler gameEndHandler;

    public StopCommand(PlayerListManager playerListManager, GameEndHandler gameEndHandler) {
        super(playerListManager);
        this.gameEndHandler = gameEndHandler;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!checkPermission(sender)) return true;

        if (!(sender instanceof Player)) {
            sender.sendMessage(Component.text("Эту команду может выполнить только игрок.").color(NamedTextColor.RED));
            return true;
        }

        Player player = (Player) sender;
        gameEndHandler.endGame(player); // Вызываем метод завершения игры
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList(); // Нет аргументов для автодополнения
    }
}