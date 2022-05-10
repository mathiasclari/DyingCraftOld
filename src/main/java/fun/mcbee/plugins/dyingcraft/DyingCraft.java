package fun.mcbee.plugins.dyingcraft;


import fun.mcbee.plugins.dyingcraft.Guns.GunWeapon;
import fun.mcbee.plugins.dyingcraft.listeners.MovementListener;
import fun.mcbee.plugins.dyingcraft.listeners.ShootingListener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class DyingCraft extends JavaPlugin {


    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new DoubleJump(), this);
        getServer().getPluginManager().registerEvents(new StaminaSystem(), this);
    }

    @Override
    public void onDisable() {}
}
