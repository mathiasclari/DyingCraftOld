package fun.mcbee.plugins.dyingcraft.ShopGUI;

import fun.mcbee.plugins.dyingcraft.DyingCraft;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GunShopGUI implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("gunshop.use")) {
                OpenShopInv(player, 0);
            }else{
                player.sendMessage(ChatColor.of("#F23700") + "You do not have required permissions to use this command!");
            }}
        return false;
    }

    private void OpenShopInv(Player player, int page) {
        Inventory shopMenu = Bukkit.createInventory(null, 54, ChatColor.of("#FFBF00") + "GunGUI");

        List<String> keys = new ArrayList<String>(DyingCraft.gunsWeapons.keySet());
        Collections.sort(keys);

        int index = 0;
        for(int i = 45 * page; i < 45 * (page + 1) && i < keys.size(); i++) {
            String key = keys.get(i);
            ItemStack item = DyingCraft.gunsWeapons.get(key).GetGunItem();
            shopMenu.setItem(index, item);
            index++;
        }

        ItemStack back = CreateItem("Back", Material.PAPER, null);
        ItemStack next = CreateItem("Next", Material.PAPER, null);
        shopMenu.setItem(45, back);
        shopMenu.setItem(49, CreateItem("Page: " + (page + 1), Material.BOOK, null));
        shopMenu.setItem(53, next);
        player.openInventory(shopMenu);
    }

    private ItemStack CreateItem(String name, Material material, List<String> lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        for(Attribute a : Attribute.values()) {
            meta.removeAttributeModifier(a);
        }
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void ClickEvents(InventoryClickEvent event) {
        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.of("#FFBF00") + "GunGUI") && event.getCurrentItem() != null) {
            int slot = event.getSlot();
            ItemStack item = event.getClickedInventory().getItem(49);
            int page = Integer.parseInt(item.getItemMeta().getDisplayName().substring(6)) - 1;
            if(slot >= 45) {
                if(slot == 45) {// back
                    if(page > 0) {
                        OpenShopInv((Player) event.getWhoClicked(), page - 1);
                    }
                } else if(slot == 53) {// next
                    if(page * 45 < DyingCraft.gunsWeapons.size()) {
                        OpenShopInv((Player) event.getWhoClicked(), page + 1);
                    }
                }
            } else {
                List<String> keys = new ArrayList<String>(DyingCraft.gunsWeapons.keySet());
                Collections.sort(keys);
                String key = keys.get(page * 45 + slot);
                ItemStack itemGun = DyingCraft.gunsWeapons.get(key).GetGunItem();
                ((Player) event.getWhoClicked()).getInventory().addItem(itemGun);
            }
            event.setCancelled(true);
        }
    }



}
