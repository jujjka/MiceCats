package micerat.micecats.listeners.user;

import micerat.micecats.GamePlayer;
import micerat.micecats.MiceCats;
import micerat.micecats.cmds.PluginMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.nio.Buffer;
import java.util.Arrays;

public class PlayerHelper {

    public void setupItems(Player player){
        player.getInventory().setItem(0,createGuiItem(Material.LEGACY_WATCH, PluginMessage.getTitle(player,"i.role"),44445,false));
        player.getInventory().setItem(1,createGuiItem(Material.BOOK,PluginMessage.getTitle(player,"i.timer"),44444,true));
        player.getInventory().setItem(4,createGuiItem(Material.RED_CONCRETE,PluginMessage.getTitle(player,"i.ready"),44446,false));
        player.getInventory().setItem(8,createGuiItem(Material.FEATHER,PluginMessage.getTitle(player,"i.person_settings"),44447,false));
    }
    public void setupItemsDeath(Player player){
        player.getInventory().setItem(0,createGuiItem(Material.LEGACY_WATCH, PluginMessage.getTitle(player,"i.role"),44445,false));
        player.getInventory().setItem(8,createGuiItem(Material.FEATHER,PluginMessage.getTitle(player,"i.person_settings"),44447,false));
    }
    public ItemStack createGuiItem(final Material material, final String name, final int model,final boolean ecnh,final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        meta.setCustomModelData(model);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);

        if(ecnh){
            meta.addEnchant(Enchantment.LUCK,1,true);
        }

        item.setItemMeta(meta);


        return item;
    }
    public void giveCompass(Player player,Player target){
        ItemStack i = createGuiItem(Material.COMPASS,PluginMessage.getTitle(player,"i.compass"),397123,true);
        CompassMeta compassMeta = (CompassMeta) i.getItemMeta();
        compassMeta.setLodestone(target.getLocation());
        compassMeta.setLodestoneTracked(false);
        i.setItemMeta(compassMeta);
        player.getInventory().setItem(4,i);
    }
    public void giveCompass(Player player,Player target, int slot){
        ItemStack i = createGuiItem(Material.COMPASS,PluginMessage.getTitle(player,"i.compass"),397123,true);
        CompassMeta compassMeta = (CompassMeta) i.getItemMeta();
        compassMeta.setLodestone(target.getLocation());
        compassMeta.setLodestoneTracked(false);
        i.setItemMeta(compassMeta);
        player.getInventory().setItem(slot,i);
    }
    public void giveCompass(Player player){
        ItemStack i = createGuiItem(Material.COMPASS,PluginMessage.getTitle(player,"i.compass"),397122,false);
        CompassMeta compassMeta = (CompassMeta) i.getItemMeta();
        i.setItemMeta(compassMeta);
        i.getItemMeta().addEnchant(Enchantment.LUCK,1,false);
        player.getInventory().setItem(4,i);
    }
}
