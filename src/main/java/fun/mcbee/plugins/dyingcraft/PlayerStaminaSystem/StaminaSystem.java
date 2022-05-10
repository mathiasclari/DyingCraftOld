package fun.mcbee.plugins.dyingcraft.PlayerStaminaSystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class StaminaSystem implements Listener {

    @EventHandler
    public void CancelFoodLevelChangeEvent(FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();
     if(e.getEntity() instanceof Player) {
         if(p.isSprinting()||p.isSneaking()) {

             e.setCancelled(true);
         }
        }
    }

    @EventHandler
    public void Movement(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.isSprinting()) {
            p.setWalkSpeed(0.23f);

        }
        if (p.isSneaking()) {
            p.setWalkSpeed(0.33f);
        }
    }
}
