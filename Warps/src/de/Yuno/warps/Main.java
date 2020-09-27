package de.Yuno.warps;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {
    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getCommand("warp").setExecutor(new warp());
    }

    public static Plugin getPlugin() {return plugin;}

    public static FileConfiguration getWarps() {
        File warps = new File(Main.getPlugin().getDataFolder() , "warps.yml");
        return YamlConfiguration.loadConfiguration(warps);
    }

    public static void saveWarps(FileConfiguration warpsConfig) {
        File warps = new File(Main.getPlugin().getDataFolder() , "warps.yml");
        try {
            warpsConfig.save(warps);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
