package com.butterycode.cubefruit;

import com.butterycode.cubefruit.commands.*;
import com.butterycode.cubefruit.tweaks.NetherFlambe;
import com.butterycode.cubefruit.tweaks.Stonecutter;
import com.butterycode.cubefruit.tweaks.Tweaks;
import com.butterycode.cubefruit.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;

public final class Main extends JavaPlugin implements Listener {

    private static Main plugin;
    private HashMap<String, DataStorage> dataFiles = new HashMap<>();
    private static LocaleManager localeInstance;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        plugin = this;

        // Create a locale instance
        localeInstance = new LocaleManager(plugin, "en_us");
        localeInstance.setDefaultLocale(getConfig().getString("plugin.language"));

        // Setup Plugin
        Bukkit.getServer().getPluginManager().registerEvents(new IdlePlayers(), plugin);
        IdlePlayers.start();
        Bukkit.getServer().getPluginManager().registerEvents(new Caboodle(), plugin);
        Caboodle.start();
        PluginSupport.setup();

        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        Bukkit.getServer().getPluginManager().registerEvents(new InfoSaver(), plugin);
        InfoSaver.start();

        // Enable Basic Features
        enableFeatures();

        // Register Commands
        registerCommands();

        Caboodle.log(plugin, "Successfully loaded.", Caboodle.LogType.INFO);
    }

    private void registerCommands() { // TODO: finish adding descriptions
        CubeFruit.start();
        getCommand("cubefruit").setExecutor(new CubeFruit());

        if (config().getBoolean("commands.message.enabled")) {
            new Message().registerCommands(plugin);
        }

        if (config().getBoolean("commands.skipday.enabled")) {
            new Skipday().registerCommands(plugin);
        }

        if (config().getBoolean("commands.item.enabled")) {
            new Item().registerCommands(plugin);
        }

        if (config().getBoolean("commands.home.enabled")) {
            Home home = new Home();

            Bukkit.getServer().getPluginManager().registerEvents(home, plugin);
            home.registerCommands(plugin);
        }

        if (config().getBoolean("commands.spawn.enabled")) {
            Spawn spawn = new Spawn();

            Bukkit.getServer().getPluginManager().registerEvents(spawn, plugin);
            spawn.registerCommands(plugin);
        }

        if (config().getBoolean("commands.tpa.enabled")) {
            new Tpa().registerCommands(plugin);
        }

        if (config().getBoolean("commands.respawn.enabled")) {
            new Respawn().registerCommands(plugin);
        }

        if (config().getBoolean("commands.worldwhitelist.enabled")) {
            WorldWhitelist worldWhitelist = new WorldWhitelist();

            Bukkit.getServer().getPluginManager().registerEvents(worldWhitelist, plugin);
            worldWhitelist.registerCommands(plugin);
        }

        if (config().getBoolean("commands.gamemode.enabled")) {
            new Gamemode().registerCommands(plugin);
        }

        if (config().getBoolean("commands.fly.enabled")) {
            new Fly().registerCommands(plugin);
        }

        if (config().getBoolean("commands.heal.enabled")) {
            new Heal().registerCommands(plugin);
        }

        if (config().getBoolean("commands.mutechat.enabled")) {
            Mutechat mutechat = new Mutechat();

            Bukkit.getServer().getPluginManager().registerEvents(mutechat, plugin);
            mutechat.registerCommands(plugin);
        }

        if (config().getBoolean("commands.speed.enabled")) {
            new Speed().registerCommands(plugin);
        }

        if (config().getBoolean("commands.report.enabled")) {
            new Report().registerCommands(plugin);
        }

        if (config().getBoolean("commands.whois.enabled")) {
            new Whois().registerCommands(plugin);
        }

        if (config().getBoolean("commands.rules.enabled")) {
            new Rules().registerCommands(plugin);
        }

        if (config().getBoolean("commands.launch.enabled")) {
            new Launch().registerCommands(plugin);
        }

        if (config().getBoolean("commands.warp.enabled")) {
            new Warp().registerCommands(plugin);
        }

        if (config().getBoolean("commands.teleport.enabled")) {
            new Teleport().registerCommands(plugin);
        }

        if (config().getBoolean("commands.sudo.enabled")) {
            new Sudo().registerCommands(plugin);
        }

        if (config().getBoolean("commands.back.enabled")) {
            Back back = new Back();

            Bukkit.getServer().getPluginManager().registerEvents(back, plugin);
            back.registerCommands(plugin);
        }
    }

    private void enableFeatures() {
        Bukkit.getServer().getPluginManager().registerEvents(new playerChat(), plugin);

        Bukkit.getServer().getPluginManager().registerEvents(new JoinAndLeave(), plugin);

        Bukkit.getServer().getPluginManager().registerEvents(new HaltEvents(), plugin);
        HaltEvents.start();

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerNametag(), plugin);
        PlayerNametag.start();

        if (config().getBoolean("halt.freeze-command")) {
            Caboodle.registerCommand(Arrays.asList(new String[] {"freeze"}));
            getCommand("freeze").setExecutor(new HaltEvents());
        }

        if (config().getBoolean("lives.enabled")) {
            Bukkit.getServer().getPluginManager().registerEvents(new playerLives(), plugin);
            playerLives.start();
            Caboodle.registerCommand(Arrays.asList(new String[] {"lives"}));
            getCommand("lives").setExecutor(new playerLives());

            if (config().getBoolean("lives.revival")) {
                Caboodle.registerCommand(Arrays.asList(new String[] {"revive"}));
                getCommand("revive").setExecutor(new playerLives());
            }
        }

        if (config().getBoolean("locks.enabled")) {
            Bukkit.getServer().getPluginManager().registerEvents(new lockedBlocks(), plugin);
            lockedBlocks.start();

            if (config().getBoolean("locks.master-key")) {
                Caboodle.registerCommand(Arrays.asList(new String[] {"getmasterkey"}));
                getCommand("getmasterkey").setExecutor(new lockedBlocks());
            }
        }

        Bukkit.getServer().getPluginManager().registerEvents(new Performance(), plugin);
        Performance.start();

        if (config().getBoolean("only-proxy.enabled")) { // TODO: test
            Bukkit.getServer().getPluginManager().registerEvents(new OnlyProxy(), plugin);
        }

        if (config().getBoolean("resource-packs.enabled")) {
            Bukkit.getServer().getPluginManager().registerEvents(new ResourcePacks(), plugin);
        }

        enableTweaks();
    }

    private void enableTweaks() {
        Bukkit.getServer().getPluginManager().registerEvents(new Tweaks(), plugin);
        Tweaks.start();

        if (config().getBoolean("tweaks.stonecutterhurt.enabled")) {
            Bukkit.getServer().getPluginManager().registerEvents(new Stonecutter(), plugin);
            Stonecutter.start();
        }

        if (config().getBoolean("tweaks.nether-flambe.enabled")) {
            Bukkit.getServer().getPluginManager().registerEvents(new NetherFlambe(), plugin);
            NetherFlambe.start();
        }
    }

    @Override
    public void onDisable() {
        // Unload classes
        CubeFruit.end();

        InfoSaver.end();

        // Save data
        saveDataFiles();

        Caboodle.log(plugin, "Successfully unloaded.", Caboodle.LogType.INFO);
    }

    public static Main plugin() {
        return plugin;
    }

    public static LocaleManager locale() {
        return localeInstance;
    }

    public FileConfiguration config() {
        return getConfig();
    }

    public void reload() {
        reloadConfig();
        saveDataFiles();

        Caboodle.log(plugin, "Reload complete.", Caboodle.LogType.INFO);
    }

    public void saveDataFiles() {
        for (String key : dataFiles.keySet()) {
            dataFiles.get(key).save();
        }
    }

    public DataStorage getData(String filepath) {
        if (!dataFiles.containsKey(filepath)) {
            dataFiles.put(filepath, new DataStorage(plugin, filepath));
        }
        return dataFiles.get(filepath);
    }
}
