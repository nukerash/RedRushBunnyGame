package net.s0ckett.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ClearCommand extends RBCommandBase {
    public ClearCommand(PlayerListManager playerListManager) {
        super(playerListManager);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        playerListManager.getPlayerList().clear();
        sender.sendMessage(Component.text("Список игроков очищен.").color(NamedTextColor.GREEN));
        return true;
    }
}