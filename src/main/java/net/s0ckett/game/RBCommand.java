package net.s0ckett.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class RBCommand extends Command {
    private final Map<String, RBCommandBase> commands = new HashMap<>();
    private final GameEndHandler gameEndHandler; // Добавьте это поле

    public RBCommand(PlayerListManager playerListManager, Main plugin, PluginConfig pluginConfig) {
        super("rb");
        this.gameEndHandler = new GameEndHandler(playerListManager, plugin); // Инициализируйте поле
        commands.put("add", new AddCommand(playerListManager));
        commands.put("remove", new RemoveCommand(playerListManager));
        commands.put("list", new ListCommand(playerListManager));
        commands.put("alert", new AlertCommand(playerListManager, plugin));
        commands.put("resistance", new ResistanceCommand(playerListManager, pluginConfig));
        commands.put("rc", new ResistanceCommand(playerListManager, pluginConfig));
        commands.put("tpall", new TeleportAllCommand(playerListManager));
        commands.put("wb", new WorldBorderCommand(playerListManager, plugin));
        commands.put("reload", new ReloadCommand(playerListManager, plugin));
        commands.put("spectator", new SpectatorCommand(playerListManager, pluginConfig));
        commands.put("spc", new SpectatorCommand(playerListManager, pluginConfig));
        commands.put("help", new HelpCommand(playerListManager));
        commands.put("clear", new ClearCommand(playerListManager));
        commands.put("status", new StatusCommand(playerListManager, pluginConfig));
        commands.put("stop", new StopCommand(playerListManager, gameEndHandler));
        commands.put("setspawn", new SetSpawnCommand(playerListManager, plugin));
        commands.put("pvp", new PvPCommand(playerListManager));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            sender.sendMessage(Component.text("Используйте: /rb help для списка команд.").color(NamedTextColor.RED));
            return false;
        }

        String subCommand = args[0].toLowerCase();
        RBCommandBase command = commands.get(subCommand);

        if (command == null) {
            sender.sendMessage(Component.text("Неизвестная команда. Используйте /rb help для списка команд.").color(NamedTextColor.RED));
            return false;
        }

        // Передаем оставшиеся аргументы (начиная с args[1])
        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, newArgs.length);

        return command.execute(sender, newArgs);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // Автодополнение для первой части команды (например, /rb <tab>)
            String input = args[0].toLowerCase();
            for (String command : commands.keySet()) {
                if (command.startsWith(input)) {
                    completions.add(command);
                }
            }
        } else if (args.length > 1) {
            // Автодополнение для аргументов команд (например, /rb resistance <tab>)
            String subCommand = args[0].toLowerCase();
            RBCommandBase command = commands.get(subCommand);

            if (command != null) {
                // Передаем оставшиеся аргументы (начиная с args[1])
                String[] newArgs = new String[args.length - 1];
                System.arraycopy(args, 1, newArgs, 0, newArgs.length);

                // Вызываем метод tabComplete для конкретной команды
                completions = command.tabComplete(sender, newArgs);
            }
        }

        return completions;
    }
}