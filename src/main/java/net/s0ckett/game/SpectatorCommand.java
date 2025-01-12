package net.s0ckett.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SpectatorCommand extends RBCommandBase {
    private final PluginConfig pluginConfig; // Изменено с Main на PluginConfig

    public SpectatorCommand(PlayerListManager playerListManager, PluginConfig pluginConfig) { // Изменено с Main на PluginConfig
        super(playerListManager);
        this.pluginConfig = pluginConfig;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!checkPermission(sender)) return true;

        if (args.length < 1) {
            sender.sendMessage(Component.text("Используйте: /rb spectator <on|off>").color(NamedTextColor.RED));
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "on":
                pluginConfig.setSpectatorOnDeath(true); // Используем новый метод
                sender.sendMessage(Component.text("Режим наблюдателя после смерти включен.").color(NamedTextColor.GREEN));
                break;

            case "off":
                pluginConfig.setSpectatorOnDeath(false); // Используем новый метод
                sender.sendMessage(Component.text("Режим наблюдателя после смерти отключен.").color(NamedTextColor.RED));
                break;

            default:
                sender.sendMessage(Component.text("Используйте: /rb spectator <on|off>").color(NamedTextColor.RED));
        }

        return true;
    }
}