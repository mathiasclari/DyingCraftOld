package fun.mcbee.plugins.dyingcraft;


import fun.mcbee.plugins.dyingcraft.GunSystem.ShootingData;
import fun.mcbee.plugins.dyingcraft.Guns.AmmoType;
import fun.mcbee.plugins.dyingcraft.Guns.GunWeapon;
import fun.mcbee.plugins.dyingcraft.ShopGUI.GunShopGUI;
import fun.mcbee.plugins.dyingcraft.listeners.MovementListener;
import fun.mcbee.plugins.dyingcraft.listeners.ShootingListener;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class DyingCraft extends JavaPlugin {
    private final YamlConfiguration conf = new YamlConfiguration();
    Map<String, GunWeapon> gunsWeapons = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new MovementListener(), this);
        getServer().getPluginManager().registerEvents(new ShootingListener(), this);
        getServer().getPluginManager().registerEvents(new GunShopGUI(), this);

        getCommand("gshop").setExecutor(new GunShopGUI());

        LoadConfig();
    }

    private void LoadConfig() {
        Configuration config = getConfig();

        ConfigurationSection cs = config.getConfigurationSection("guns");
        if(cs != null) {
            Set<String> sets = cs.getKeys(false);
            for(String key : sets) {
                String startPart = "guns." + key;

                int maxAmo = config.getInt(startPart + ".max_ammo");
                long delayShooting = config.getLong(startPart + ".reload_time");
                Material itemMaterial = Material.valueOf(config.getString(startPart + ".material"));
                String gunName = config.getString(startPart + ".display_name");
                int gunId = Integer.parseInt(key);
                int damage = config.getInt(startPart + ".damage");
                int bulletPerShot = config.getInt(startPart + ".bullet_per_shot");
                float accuracy = (float) config.getDouble(startPart + ".accuracy");
                int range = config.getInt(startPart + ".range");
                double recoil = config.getDouble(startPart + ".recoil");
                AmmoType ammoType = AmmoType.valueOf(config.getString(startPart + ".ammo_type"));
                Sound soundShooting = Sound.valueOf(config.getString(startPart + ".shooting_sound"));
                Sound soundReloading = Sound.valueOf(config.getString(startPart + ".reloading_sound"));
                Sound soundEmpty = Sound.valueOf(config.getString(startPart + ".empty_sound"));

                gunsWeapons.put(key, new GunWeapon(maxAmo, delayShooting, itemMaterial, gunName, gunId, damage,
                        bulletPerShot, range, accuracy, recoil, ammoType, soundShooting, soundReloading, soundEmpty));

            }
        }
    }

    @Override
    public void onDisable() {}
}
