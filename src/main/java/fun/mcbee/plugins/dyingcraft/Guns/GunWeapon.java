package fun.mcbee.plugins.dyingcraft.Guns;

import fun.mcbee.plugins.dyingcraft.GunSystem.ShootingData;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GunWeapon {

    private ShootingData shootingData;
    private int maxAmo;
    private long delayShooting;
    private Material itemMaterial;
    private String gunName;
    private int gunId;
    private int damage;
    private int bulletPerShot;
    private float accuracy;
    private int range;
    private double recoil;
    private AmmoType ammoType;
    private Sound soundShooting;
    private Sound soundReloading;
    private Sound soundEmpty;

    public GunWeapon(int maxAmo, long delayShooting, Material itemMaterial, String gunName, int gunId, int damage, int bulletPerShot, int range,
                     float accuracy, double recoil, AmmoType ammoType, Sound soundShooting, Sound soundReloading, Sound soundEmpty) {
        this.maxAmo = maxAmo;
        this.delayShooting = delayShooting;
        this.itemMaterial = itemMaterial;
        this.gunName = gunName;
        this.gunId = gunId;
        this.damage = damage;
        this.bulletPerShot = bulletPerShot;
        this.accuracy = accuracy;
        this.recoil = recoil;
        this.ammoType = ammoType;
        this.soundEmpty = soundEmpty;
        this.soundShooting = soundShooting;
        this.soundReloading = soundReloading;
        this.range = range;
        Reload();
    }
    public void Reload() {
        shootingData = new ShootingData(this.delayShooting, this.maxAmo);
    }

    public ItemStack GetGunItem() {
        ItemStack item = new ItemStack(itemMaterial);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(this.gunName);
        meta.setLocalizedName("id:" + this.gunId);
        List<String> lore = new ArrayList<String>();
        lore.add("Damage: " + this.damage);
        lore.add("Accuracy: " + this.accuracy);
        lore.add("Fire Rate: " + String.format("%.2f", (this.delayShooting/1000d)));
        lore.add("Recoil: " + this.recoil);
        lore.add("");
        lore.add("Ammo: " + this.shootingData.GetShotsLeft() + "/" + this.maxAmo);
        lore.add("Type: " + this.ammoType.GetName());
        meta.setLore(lore);
        for(Attribute a : Attribute.values()) {
            meta.removeAttributeModifier(a);
        }
        item.setItemMeta(meta);
        return item;
    }

    public ShootingData GetShootingData() {
        return shootingData;
    }

    public int GetMaxAmo() {
        return maxAmo;
    }

    public long GetDelayShooting() {
        return delayShooting;
    }

    public Material GetItemMaterial() {
        return itemMaterial;
    }

    public String GetGunName() {
        return gunName;
    }

    public int GetGunId() {
        return gunId;
    }

    public int GetDamage() {
        return damage;
    }

    public int GetBulletPerShot() {
        return this.bulletPerShot;
    }

    public float GetAccuracy() {
        return accuracy;
    }

    public double GetRecoil() {
        return recoil;
    }

    public AmmoType GetAmmoType() {
        return this.ammoType;
    }

    public Sound GetSoundShooting() {
        return this.soundShooting;
    }

    public Sound GetSoundReloading() {
        return this.soundReloading;
    }

    public Sound GetSoundEmpty() {
        return this.soundEmpty;
    }

    public int GetRange() {
        return this.range;
    }

}
