package micerat.micecats.listeners.gui;

import micerat.micecats.GamePlayer;
import micerat.micecats.MiceCats;
import micerat.micecats.cmds.PluginMessage;
import micerat.micecats.listeners.user.PlayerHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CompassGUI$Listener implements Listener {
    @EventHandler
    public void click(InventoryClickEvent clickEvent) {
        Player holder = (Player) clickEvent.getWhoClicked();
        if (!clickEvent.getView().getTitle().equals(PluginMessage.getTitle(holder, "g.CompassGUI.title"))) {
            return;
        }
        if (clickEvent.getCurrentItem() == null) {
            return;
        }
        ItemStack I = clickEvent.getCurrentItem();
        if (!I.hasItemMeta()) {
            return;
        }
        if (!I.getItemMeta().hasCustomModelData()) {
            return;
        }
        if (!MiceCats.Is_Game) {
            return;
        }
        if (GamePlayer.getGame_player(holder) == null) {
            return;
        }
        GamePlayer gP = GamePlayer.getGame_player(holder);
        if (Bukkit.getPlayer(I.getItemMeta().getDisplayName()) == null) {
            holder.sendMessage(ChatColor.RED + "Error");
        }
        gP.setTarget(Bukkit.getPlayer(I.getItemMeta().getDisplayName()));
        holder.getInventory().remove(Material.COMPASS);
        new PlayerHelper().giveCompass(holder, gP.getTarget());
        clickEvent.setCancelled(true);
    }
}
