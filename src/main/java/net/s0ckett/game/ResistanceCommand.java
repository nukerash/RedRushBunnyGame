package net.s0ckett.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ResistanceCommand extends RBCommandBase {
    private final PluginConfig pluginConfig;
    private boolean resistanceActive = false;
    private BukkitRunnable resistanceTask;

    /**
     * Конструктор класса.
     *
     * @param playerListManager Менеджер списка игроков.
     * @param pluginConfig      Конфигурация плагина.
     */
    public ResistanceCommand(PlayerListManager playerListManager, PluginConfig pluginConfig) {
        super(playerListManager);
        this.pluginConfig = pluginConfig;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!checkPermission(sender)) return true;

        if (args.length < 1) {
            sender.sendMessage(Component.text("Используйте: /rb resistance <on|off>").color(NamedTextColor.RED));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "on":
                if (!resistanceActive) {
                    resistanceActive = true;
                    startResistanceTask();
                    sender.sendMessage(Component.text("Бессмертие включено.").color(NamedTextColor.GREEN));
                } else {
                    sender.sendMessage(Component.text("Бессмертие уже активно.").color(NamedTextColor.YELLOW));
                }
                break;

            case "off":
                if (resistanceActive) {
                    resistanceActive = false;
                    stopResistanceTask();
                    sender.sendMessage(Component.text("Бессмертие отключено.").color(NamedTextColor.RED));
                } else {
                    sender.sendMessage(Component.text("Бессмертие уже отключено.").color(NamedTextColor.YELLOW));
                }
                break;

            default:
                sender.sendMessage(Component.text("Используйте: /rb resistance <on|off>").color(NamedTextColor.RED));
        }
        return true;
    }

    private void startResistanceTask() {
        if (resistanceTask != null) return;

        resistanceTask = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 40, 255));
                }
            }
        };
        resistanceTask.runTaskTimer(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("Game")), 0L, 20L);
    }

    private void stopResistanceTask() {
        if (resistanceTask != null) {
            resistanceTask.cancel();
            resistanceTask = null;
        }
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("on", "off"); // Предлагаем "on" и "off"
        }
        return Collections.emptyList();
    }
}