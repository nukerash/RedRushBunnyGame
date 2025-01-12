package net.s0ckett.game;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class Main extends JavaPlugin {
    private PlayerListManager playerListManager;
    private PluginConfig pluginConfig;

    @Override
    public void onEnable() {
        // Сохраняем конфигурацию по умолчанию, если она отсутствует
        this.saveDefaultConfig();

        // Загружаем конфигурацию
        this.pluginConfig = new PluginConfig(getConfig());

        playerListManager = new PlayerListManager();
        registerCommand(new RBCommand(playerListManager, this, pluginConfig));

        // Регистрируем слушатель событий
        getServer().getPluginManager().registerEvents(new CraftCakeListener(playerListManager, this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this, pluginConfig), this);

        getLogger().info("RedRushBunnyGame активирован!");
    }

    @Override
    public void onDisable() {
        getLogger().info("RedRushBunnyGame отключен.");
    }

    public PlayerListManager getPlayerListManager() {
        return playerListManager;
    }

    public PluginConfig getPluginConfig() {
        return pluginConfig;
    }

    public void reloadPluginConfig() {
        this.reloadConfig(); // Перезагружает конфигурацию из файла config.yml
        this.pluginConfig = new PluginConfig(getConfig()); // Обновляет объект PluginConfig
    }

    private void registerCommand(RBCommand command) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register("rb", command);
            getLogger().info("Команда 'rb' успешно зарегистрирована.");
        } catch (Exception e) {
            getLogger().severe("Не удалось зарегистрировать команду: " + e.getMessage());
            e.printStackTrace();
        }
    }
}