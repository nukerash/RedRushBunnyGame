package net.s0ckett.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand extends RBCommandBase {
    private final Main plugin;

    public ReloadCommand(PlayerListManager playerListManager, Main plugin) {
        super(playerListManager);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!checkPermission(sender)) return true;

        plugin.reloadPluginConfig();
        sender.sendMessage(Component.text("Конфигурация плагина перезагружена.").color(NamedTextColor.GREEN));
        return true;
    }
}