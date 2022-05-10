package fun.mcbee.plugins.dyingcraft;

import org.bukkit.plugin.java.JavaPlugin;

public final class DyingCraft extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new DoubleJump(), this);
    }

    @Override
    public void onDisable() {

    }
}
