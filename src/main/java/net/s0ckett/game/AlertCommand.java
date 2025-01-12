package net.s0ckett.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AlertCommand extends RBCommandBase {
    private final Main plugin;

    public AlertCommand(PlayerListManager playerListManager, Main plugin) {
        super(playerListManager);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!checkPermission(sender)) return true;

        Set<String> players = playerListManager.getPlayerList();
        if (players.isEmpty()) {
            sender.sendMessage(Component.text("Нет игроков в списке.").color(NamedTextColor.YELLOW));
            return true;
        }

        int glowingDuration = plugin.getPluginConfig().getGlowingDuration();
        int gatewayRemoveDelay = plugin.getPluginConfig().getGatewayRemoveDelay();

        players.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .filter(Player::isOnline)
                .forEach(player -> {
                    // Применяем эффект GLOWING
                    player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20 * glowingDuration, 1));

                    // Устанавливаем END_GATEWAY на месте игрока
                    Location gatewayLocation = player.getLocation();
                    gatewayLocation.getBlock().setType(Material.END_GATEWAY);

                    // Удаляем END_GATEWAY через указанное время
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        if (gatewayLocation.getBlock().getType() == Material.END_GATEWAY) {
                            gatewayLocation.getBlock().setType(Material.AIR); // Удаляем блок
                        }
                    }, 20L * gatewayRemoveDelay);

                    // Отправляем сообщение с координатами
                    Component message = Component.text("Игрок " + player.getName() + " находится по координатам: ")
                            .color(NamedTextColor.LIGHT_PURPLE)
                            .append(Component.text("X=" + gatewayLocation.getBlockX() + " Y=" + gatewayLocation.getBlockY() + " Z=" + gatewayLocation.getBlockZ()));

                    Bukkit.broadcast(message);
                });

        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList(); // Нет аргументов для автодополнения
    }
}