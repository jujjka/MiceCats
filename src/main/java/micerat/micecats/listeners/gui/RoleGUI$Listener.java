package micerat.micecats.listeners.gui;

import micerat.micecats.GamePlayer;
import micerat.micecats.MiceCats;
import micerat.micecats.cmds.PluginMessage;
import micerat.micecats.listeners.user.PlayerHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class RoleGUI$Listener implements Listener {
    @EventHandler
    public void click(InventoryClickEvent clickEvent) {
        Player holder = (Player) clickEvent.getWhoClicked();
        if (!clickEvent.getView().getTitle().equals(PluginMessage.getTitle(holder, "g.roleGUI.title"))) {
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
        if (MiceCats.Is_Game) {
            return;
        }
        switch (I.getItemMeta().getCustomModelData()) {
            case 500000:
                if (GamePlayer.getGame_player(holder) == null && GamePlayer.getDeath_player(holder) == null) {
                    MiceCats.getGameManager().changeRole(holder, GamePlayer.Role.CAT);
                } else {
                    if (!MiceCats.Is_Game) {
                        MiceCats.getGameManager().changeRole(holder, GamePlayer.Role.CAT);
                    }
                }
                if (GamePlayer.getDeath_player(holder) != null) {
                    if (GamePlayer.getDeath_player(holder).getRole() != GamePlayer.Role.CAT
                            && GamePlayer.getDeath_player(holder).getRole() == GamePlayer.Role.MICE) {
                        if (MiceCats.getGameSetting().DEATH_RUNNERS) {
                            MiceCats.getGameManager().changeRole(holder, GamePlayer.Role.CAT);
                        }
                    }
                }
                clickEvent.setCancelled(true);
                holder.closeInventory();
                break;
            case 500001:
                if (GamePlayer.getGame_player(holder) == null && GamePlayer.getDeath_player(holder) == null) {
                    MiceCats.getGameManager().changeRole(holder, GamePlayer.Role.MICE);
                } else {
                    if (!MiceCats.Is_Game) {
                        MiceCats.getGameManager().changeRole(holder, GamePlayer.Role.MICE);
                    }
                }
                click_methods(clickEvent);
                break;
            case 500002:
                holder.closeInventory();
                MiceCats.getGameManager().changeRole(holder, GamePlayer.Role.SPECTATOR);
                break;
        }
    }

    private void click_methods(InventoryClickEvent clickEvent) {
        Player user = (Player) clickEvent.getWhoClicked();
        user.closeInventory();
        user.getInventory().setItem(7, new PlayerHelper().createGuiItem(Material.PAPER, PluginMessage.getTitle(user, "i.game_settings"), 20039, false));
    }
}
