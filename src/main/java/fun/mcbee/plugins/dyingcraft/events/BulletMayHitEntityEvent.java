package fun.mcbee.plugins.dyingcraft.events;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BulletMayHitEntityEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    private boolean didHit = true;
    private Entity entity;

    public BulletMayHitEntityEvent(Entity entity) {
        this.entity = entity;
    }

    public void setDidHit(boolean didHit) {
        this.didHit = didHit;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public boolean hasHit() {
        return this.didHit;
    }
}
