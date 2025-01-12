package net.s0ckett.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class StatusCommand extends RBCommandBase {
    private final PluginConfig pluginConfig;

    public StatusCommand(PlayerListManager playerListManager, PluginConfig pluginConfig) {
        super(playerListManager);
        this.pluginConfig = pluginConfig;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        sender.sendMessage(Component.text("===== Статус плагина =====").color(NamedTextColor.GOLD));
        sender.sendMessage(Component.text("Режим наблюдателя после смерти: ")
                .color(NamedTextColor.AQUA)
                .append(Component.text(pluginConfig.isSpectatorOnDeath() ? "Включен" : "Отключен").color(NamedTextColor.WHITE)));
        sender.sendMessage(Component.text("Скорость изменения границы мира: ")
                .color(NamedTextColor.AQUA)
                .append(Component.text(pluginConfig.getBorderSpeed() + " блоков/сек").color(NamedTextColor.WHITE)));
        return true;
    }
}