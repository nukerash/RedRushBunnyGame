package net.s0ckett.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class WorldBorderCommand extends RBCommandBase {
    private final Main plugin;

    public WorldBorderCommand(PlayerListManager playerListManager, Main plugin) {
        super(playerListManager);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!checkPermission(sender)) return true;

        if (args.length < 1) {
            // Если размер не указан, используем значение по умолчанию из конфигурации
            int defaultSize = plugin.getPluginConfig().getDefaultBorderSize();
            WorldBorder worldBorder = Bukkit.getWorlds().get(0).getWorldBorder();
            int currentSize = (int) worldBorder.getSize();
            int borderSpeed = plugin.getPluginConfig().getBorderSpeed();

            if (borderSpeed <= 0) {
                sender.sendMessage(Component.text("Скорость изменения границы должна быть больше 0.").color(NamedTextColor.RED));
                return false;
            }

            int timeInSeconds = Math.abs(currentSize - defaultSize) / borderSpeed;
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "worldborder set " + defaultSize + " " + timeInSeconds);

            sender.sendMessage(Component.text("Граница мира будет изменена до " + defaultSize + " блоков за " + timeInSeconds + " секунд.").color(NamedTextColor.GREEN));
            return true;
        }

        try {
            int newSize = Integer.parseInt(args[0]);
            if (newSize <= 0) {
                sender.sendMessage(Component.text("Размер границы должен быть больше 0.").color(NamedTextColor.RED));
                return false;
            }

            WorldBorder worldBorder = Bukkit.getWorlds().get(0).getWorldBorder();
            int currentSize = (int) worldBorder.getSize();
            int borderSpeed = plugin.getPluginConfig().getBorderSpeed();

            if (borderSpeed <= 0) {
                sender.sendMessage(Component.text("Скорость изменения границы должна быть больше 0.").color(NamedTextColor.RED));
                return false;
            }

            int timeInSeconds = Math.abs(currentSize - newSize) / borderSpeed;
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "worldborder set " + newSize + " " + timeInSeconds);

            sender.sendMessage(Component.text("Граница мира будет изменена до " + newSize + " блоков за " + timeInSeconds + " секунд.").color(NamedTextColor.GREEN));
        } catch (NumberFormatException e) {
            sender.sendMessage(Component.text("Неверный формат числа.").color(NamedTextColor.RED));
        }

        return true;
    }
}