package fun.mcbee.plugins.dyingcraft;


import fun.mcbee.plugins.dyingcraft.PlayerStaminaSystem.StaminaSystem;
import org.bukkit.plugin.java.JavaPlugin;

public final class DyingCraft extends JavaPlugin {


    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new DoubleJump(), this);
        getServer().getPluginManager().registerEvents(new StaminaSystem(), this);
    }

    @Override
    public void onDisable() {
    }
}
