package net.s0ckett.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class HelpCommand extends RBCommandBase {
    public HelpCommand(PlayerListManager playerListManager) {
        super(playerListManager);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        sender.sendMessage(Component.text("===== Помощь по командам /rb =====").color(NamedTextColor.GOLD));

        sender.sendMessage(Component.text("/rb add <ник>")
                .color(NamedTextColor.AQUA)
                .append(Component.text(" - Добавляет игрока в список.").color(NamedTextColor.WHITE)));

        sender.sendMessage(Component.text("/rb remove <ник>")
                .color(NamedTextColor.AQUA)
                .append(Component.text(" - Удаляет игрока из списка.").color(NamedTextColor.WHITE)));

        sender.sendMessage(Component.text("/rb list")
                .color(NamedTextColor.AQUA)
                .append(Component.text(" - Показывает список игроков.").color(NamedTextColor.WHITE)));

        sender.sendMessage(Component.text("/rb alert")
                .color(NamedTextColor.AQUA)
                .append(Component.text(" - Помечает игроков из списка и показывает их координаты.").color(NamedTextColor.WHITE)));

        sender.sendMessage(Component.text("/rb resistance <on|off>")
                .color(NamedTextColor.AQUA)
                .append(Component.text(" - Включает/выключает бессмертие для всех игроков.").color(NamedTextColor.WHITE)));

        sender.sendMessage(Component.text("/rb rc <on|off>")
                .color(NamedTextColor.AQUA)
                .append(Component.text(" - Алиас для /rb resistance.").color(NamedTextColor.WHITE)));

        sender.sendMessage(Component.text("/rb tpall [ник]")
                .color(NamedTextColor.AQUA)
                .append(Component.text(" - Телепортирует всех игроков из списка к вам или к указанному игроку.").color(NamedTextColor.WHITE)));

        sender.sendMessage(Component.text("/rb wb <размер>")
                .color(NamedTextColor.AQUA)
                .append(Component.text(" - Изменяет границу мира на указанный размер.").color(NamedTextColor.WHITE)));

        sender.sendMessage(Component.text("/rb reload")
                .color(NamedTextColor.AQUA)
                .append(Component.text(" - Перезагружает конфигурацию плагина.").color(NamedTextColor.WHITE)));

        sender.sendMessage(Component.text("/rb spectator <on|off>")
                .color(NamedTextColor.AQUA)
                .append(Component.text(" - Включает/выключает режим наблюдателя после смерти.").color(NamedTextColor.WHITE)));

        sender.sendMessage(Component.text("/rb spc <on|off>")
                .color(NamedTextColor.AQUA)
                .append(Component.text(" - Алиас для /rb spectator.").color(NamedTextColor.WHITE)));

        sender.sendMessage(Component.text("/rb help")
                .color(NamedTextColor.AQUA)
                .append(Component.text(" - Показывает это сообщение.").color(NamedTextColor.WHITE)));

        return true;
    }
}