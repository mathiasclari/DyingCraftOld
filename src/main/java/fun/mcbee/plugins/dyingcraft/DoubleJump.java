package fun.mcbee.plugins.dyingcraft;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.HashMap;
import java.util.Map;

public class DoubleJump implements Listener {



    @EventHandler
    public void onPlayerFly(PlayerToggleFlightEvent e) {
        Player p = e.getPlayer();



            if (p.getGameMode() != GameMode.CREATIVE) {
                e.setCancelled(true);
                p.setAllowFlight(false);
                p.setFlying(false);
                p.setVelocity(p.getLocation().getDirection().multiply(0.8D).setY(0.3D));
                p.playEffect(p.getLocation(), Effect.ELECTRIC_SPARK, 15);
                p.playEffect(p.getLocation(), Effect.ENDEREYE_LAUNCH, 15);
            }
        }

        @EventHandler
        public void onPlayerMove (PlayerMoveEvent e){
            Player p = e.getPlayer();
            if (p.getGameMode() != GameMode.CREATIVE) {
                if ((e.getPlayer().getGameMode() != GameMode.CREATIVE)
                        && (p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR)) {
                    p.setAllowFlight(true);

            }
        }
    }

}