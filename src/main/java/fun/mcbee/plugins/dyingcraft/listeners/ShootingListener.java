package fun.mcbee.plugins.dyingcraft.listeners;

import fun.mcbee.plugins.dyingcraft.GunSystem.BulletTask;
import fun.mcbee.plugins.dyingcraft.GunSystem.HitPart;
import fun.mcbee.plugins.dyingcraft.GunSystem.LocationRunnable;
import fun.mcbee.plugins.dyingcraft.GunSystem.ShootingData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.UUID;

public class ShootingListener implements Listener {

    @EventHandler
    public void PlayerShoot(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {

            //TODO need to use some playerData and get the weapon data from there
            ShootingData sd = new ShootingData(1000, 10);

            if(sd.Shoot()) {
                Location spawnLocation = player.getEyeLocation();

                BulletTask bt = new BulletTask(player.getUniqueId(), spawnLocation, spawnLocation.getDirection(), 50, 2);
                bt.SetHitTask(new Runnable() {
                    @Override
                    public void run() {
                        if(bt.GetHitBlock() != null) {
                            Location lastHitLocation = bt.GetLastHit();
                            Material m = bt.GetHitBlock().getType();
                            if(m.name().contains("GLASS")) {
                                lastHitLocation.getWorld().playSound(lastHitLocation, Sound.BLOCK_GLASS_BREAK, 0.5f, 1);
                                bt.GetHitBlock().setType(Material.AIR);
                                //lastHitLocation.getWorld().playSound(lastHitLocation, Sound.ITEM_TRIDENT_HIT, 1, 1);
                            } else {
                                lastHitLocation.getWorld().playSound(lastHitLocation, Sound.BLOCK_STONE_BREAK, 0.5f, 1);
                                //lastHitLocation.getWorld().playSound(lastHitLocation, Sound.ITEM_TRIDENT_HIT, 1, 1);
                            }
                        }

                    }
                });

                bt.SetCloseByTask(new LocationRunnable() {
                    @Override
                    public void run() {
                        Location closeByLocation = this.GetLocation();
                        if(closeByLocation != null && this.GetPlayer() != null) {
                            Location pLocation = this.GetPlayer().getEyeLocation();
                            Vector v = new Vector(closeByLocation.getX() - pLocation.getX(), closeByLocation.getY() - pLocation.getY(), closeByLocation.getZ() - pLocation.getZ());
                            this.GetPlayer().playSound(closeByLocation.clone().add(v.normalize().multiply(8)), Sound.ENTITY_PLAYER_ATTACK_WEAK, 0.5f, 1);
                        }
                    }
                });

                bt.Shoot();

                Map<UUID, HitPart> data = bt.GetHitData();

                //TODO get data and do something to this hit entities which are provided in UUIDs form.

            }

        }
    }

}
