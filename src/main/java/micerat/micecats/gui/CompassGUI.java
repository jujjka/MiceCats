package micerat.micecats.gui;

import lombok.Getter;
import micerat.micecats.GamePlayer;
import micerat.micecats.GameVoting;
import micerat.micecats.MiceCats;
import micerat.micecats.cmds.PluginMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Async;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;

public class CompassGUI implements InventoryHolder {
    private Inventory gui;
    @Getter
    private Player holder;

    public CompassGUI(Player holder_pl){
        this.holder = holder_pl;
        this.gui = Bukkit.createInventory(holder,18, PluginMessage.getTitle(holder,"g.CompassGUI.title"));

        loadItems();
        holder.openInventory(gui);
    }
    private void loadItems(){
        Iterator<GamePlayer> gpI = GamePlayer.getGame_players().listIterator();
        while (gpI.hasNext()){
            GamePlayer gP = gpI.next();
            GamePlayer gpMain = GamePlayer.getGame_player(holder);
            //todo проверку на команду
                if (gP.getPlayer().isOnline()) {
                    if (gpMain.getTarget() == gP.getPlayer()) {
                        gui.addItem(createGuiItem(
                                Material.PLAYER_HEAD,
                                gP.getPlayer().getName(),
                                true,
                                20092008,
                                PluginMessage.getTitle(holder, "g.CompassGUI.lore", String.valueOf((int)gP.getPlayer().getLocation().distance(gpMain.getPlayer().getLocation())))));
                    } else {
                        gui.addItem(createGuiItem(
                                Material.PLAYER_HEAD,
                                gP.getPlayer().getName(),
                                false,
                                20092008,
                                PluginMessage.getTitle(holder, "g.CompassGUI.lore", String.valueOf((int)gP.getPlayer().getLocation().distance(gpMain.getPlayer().getLocation())))));
                    }
                }
        }
    }
    protected ItemStack createGuiItem(final Material material, final String name, boolean ench,final int model,String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        meta.setCustomModelData(model);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        if(ench){
            meta.addEnchant(Enchantment.LUCK,1,false);
        }
        item.setItemMeta(meta);


        return item;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return gui;
    }

}
