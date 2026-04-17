package ru.euj3ne.e3voidteleporter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private final double triggerHeight;
    private final Location teleportLocation;

    public PlayerMoveListener(E3VoidTeleporterMain plugin) {
        var config = plugin.getConfig();
        this.triggerHeight = config.getDouble("settings.triggerHeight");

        String worldName = config.getString("settings.teleportLocation.world");
        if (worldName == null || worldName.isEmpty()) {
            plugin.getLogger().warning("World name is missing in config.");
            this.teleportLocation = null;
            return;
        }

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            plugin.getLogger().warning("World '" + worldName + "' not found. Players will not be teleported.");
            this.teleportLocation = null;
            return;
        }

        double x = config.getDouble("settings.teleportLocation.x");
        double y = config.getDouble("settings.teleportLocation.y");
        double z = config.getDouble("settings.teleportLocation.z");
        float yaw = (float) config.getDouble("settings.teleportLocation.yaw");
        float pitch = (float) config.getDouble("settings.teleportLocation.pitch");

        this.teleportLocation = new Location(world, x, y, z, yaw, pitch);
    }

    @EventHandler
    public void onPlayerFall(PlayerMoveEvent event) {
        if (teleportLocation == null) return;
        if (event.getFrom().getY() == event.getTo().getY()) return;

        Player player = event.getPlayer();
        if (player.getLocation().getY() < triggerHeight) {
            player.teleport(teleportLocation);
        }
    }
}
