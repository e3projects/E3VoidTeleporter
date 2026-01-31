package ru.euj3ne.e3voidteleporter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class E3VoidTeleporter extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(this, this);

        getLogger().info("Plugin has been enabled!");
        getLogger().info("Plugin developed by: @euj3ne");
        getLogger().info("Website: " + getPluginMeta().getWebsite());
    }

    @EventHandler
    public void onPlayerFall(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        String worldName = getConfig().getString("toTeleport.world");
        World world = Bukkit.getWorld(worldName);
        if(world == null) {
            getLogger().warning("World '" + worldName + "' not found!");
            return;
        }

        if (player.getLocation().getY() < getConfig().getInt("height") && player.getLocation().getY() > 0) {
            player.teleport(new Location(Bukkit.getWorld(getConfig().getString("toTeleport.world")), 
            getConfig().getDouble("toTeleport.x"), 
            getConfig().getDouble("toTeleport.y"), 
            getConfig().getDouble("toTeleport.z"), 
            (float) getConfig().getInt("toTeleport.yaw"), 
            (float) getConfig().getInt("toTeleport.pitch")));
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin has been disabled!");
    }
}
