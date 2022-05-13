package fun.mcbee.plugins.dyingcraft.GunSystem;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class LocationRunnable implements Runnable {

    private Location location;
    private Player player;

    public void SetLocation(Location location) {
        this.location = location;
    }

    public Location GetLocation() {
        return this.location;
    }

    public void SetPlayer(Player player) {
        this.player = player;
    }

    public Player GetPlayer() {
        return this.player;
    }
}