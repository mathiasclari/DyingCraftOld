package fun.mcbee.plugins.dyingcraft.Guns;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public enum AmmoType {

    Pistol_Ammo("Pistol_Ammo", Material.FIREWORK_STAR),
    Assault_Rifle_Ammo("Assault_Rifle_Ammo", Material.NETHER_BRICK),
    Shotgun_Ammo("Shotgun_Ammo", Material.NETHER_WART),
    Sniper_Rifle_Ammo("Sniper_Rifle_Ammo", Material.INK_SAC),
    LMG_Ammo("LMG_Ammo", Material.SLIME_BALL),
    SMG_Ammo("SMG_Ammo", Material.CLAY_BALL),
    ;

    private String name;
    private Material material;

    private AmmoType(String name, Material material) {
        this.name = name;
    }

    public String GetName() {
        return this.name;
    }

    private Material GetMaterial() {
        return this.material;
    }

    private ItemStack GetItem(int count) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(this.name);
        List<String> lore = new ArrayList<String>();
        lore.add("");
        meta.setLore(lore);
        for(Attribute a : Attribute.values()) {
            meta.removeAttributeModifier(a);
        }
        item.setItemMeta(meta);
        return item;
    }

}
