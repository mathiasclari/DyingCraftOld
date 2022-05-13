package fun.mcbee.plugins.dyingcraft.GunSystem;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import fun.mcbee.plugins.dyingcraft.events.BulletMayHitEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class BulletTask {

    private double maxLength;
    private final double step = 0.1;
    private double length;
    private int canPenetrateEntityCount = 1;

    private Runnable runnableHit;
    private LocationRunnable runnableCloseBy;

    private Location origin;
    private Vector direction;
    private UUID uuid;

    private Location lastHitLocation;
    private Block hitBlock;
    private Map<UUID, HitPart> hitEntities = new HashMap<UUID, HitPart>();
    private Map<Player, Location> misses = new HashMap<Player, Location>();

    public BulletTask(UUID uuid, Location origin, Vector direction, double maxLength, int canPenetrateEntityCount) {
        this.direction = direction.multiply(step);
        this.canPenetrateEntityCount = canPenetrateEntityCount;
        this.maxLength = maxLength;
        this.origin = origin;
        this.uuid = uuid;
    }

    public void SetHitTask(Runnable runnableHit) {
        this.runnableHit = runnableHit;
    }

    public void SetCloseByTask(LocationRunnable runnableCloseBy) {
        this.runnableCloseBy = runnableCloseBy;
    }

    public Map<UUID, HitPart> GetHitData() {
        return hitEntities;
    }

    public Block GetHitBlock() {
        return hitBlock;
    }

    public Location GetLastHit() {
        return lastHitLocation;
    }

    public void Shoot() {
        for(length = 0; length < maxLength / step; length += step) {
            Location l = origin.clone().add(direction.clone().multiply(length));
            this.lastHitLocation = l;
            if(HasCollided(l)) {
                break;
            }
        }
        End();
    }

    private void End() {
        if(runnableHit != null) {
            runnableHit.run();
        }
        for(Entry<Player, Location> en : misses.entrySet()) {
            runnableCloseBy.SetPlayer(en.getKey());
            runnableCloseBy.SetLocation(en.getValue());
            runnableCloseBy.run();
        }
        if(hitEntities.size() == 0) {
            return;
        }
    }

    private boolean HasCollided(Location location) {
        Block b = location.getBlock();
        if(!b.isEmpty() && !Tag.CARPETS.isTagged(b.getType())) {
            this.hitBlock = b;
            return true;
        }
        Collection<Entity> ens = location.getWorld().getNearbyEntities(location, 0.3, 0.3, 0.3);
        for(Entity en : ens) {
            if(!en.getUniqueId().equals(uuid)) {
                BulletMayHitEntityEvent event = new BulletMayHitEntityEvent(en);
                Bukkit.getPluginManager().callEvent(event);
                if(event.hasHit()) {
                    if(en instanceof Player) {
                        if(!hitEntities.containsKey(en.getUniqueId())) {
                            HitPart hp = GetHitPart((Player) en, location);
                            if(hp != null) {
                                if(hp.equals(HitPart.Near)) {
                                    if(!misses.containsKey((Player) en)) {
                                        misses.put((Player) en, location);
                                    }
                                } else {
                                    if(misses.containsKey((Player) en)) {
                                        misses.remove((Player) en);
                                    }
                                    hitEntities.put(en.getUniqueId(), hp);
                                    canPenetrateEntityCount--;
                                    if(canPenetrateEntityCount == 0) {
                                        return true;
                                    }
                                }
                            }
                        }
                    } else {
                        //TODO do stuff for other entities!
                    }
                }
            }
        }
        return false;
    }

    private HitPart GetHitPart(Player player, Location location) {

        double yaw = (player.getLocation().getYaw() / 180) * Math.PI;
        double y = location.getY() - player.getLocation().getY();
        double xS = location.getX() - player.getLocation().getX();
        double zS = location.getZ() - player.getLocation().getZ();

        double d = Math.sqrt(xS * xS + zS * zS);
        double cYaw = Math.atan2(zS, xS);

        double uYaw = cYaw - yaw;

        double x = Math.cos(uYaw) * d;
        double z = Math.sin(uYaw) * d;

        if(player.isSneaking()) {

        } else {
            if(x < 0.25 && x > -0.25) {
                if(z < 0.25 && z > -0.25) {
                    if(y > 1.5 && y < 1.75) {
                        return HitPart.Head;
                    } else if(y > 1.75 && y < 1.9) {
                        return HitPart.Helmet;
                    }
                    if(z < 0.125 && z > -0.125) {
                        if(y > 0 && y < 0.75) {
                            return HitPart.Legs;
                        } else if(y > 0.75 && y < 1.12) {
                            return HitPart.Stomach;
                        } else if(y > 1.12 && y < 1.5) {
                            return HitPart.Chest;
                        }
                    }
                }
            } else if(x > 0.25 && x < 0.5 || x < -0.25 && x > -0.5) {
                if(z < 0.125 && z > -0.125) {
                    if(y > 0.75 && y < 1.5) {
                        return HitPart.Arms;
                    }
                }
            }
            if(runnableCloseBy != null) {
                if(x < 0.45 && x > -0.45) {
                    if(z < 0.45 && z > -0.45) {
                        if(y > 1.5 && y < 2.1) {
                            return HitPart.Near;
                        }
                    }
                }
            }
        }
        return null;
    }

}