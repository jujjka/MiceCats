package micerat.micecats.listeners.gui;

import micerat.micecats.MiceCats;
import micerat.micecats.cmds.PluginMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PersonalLangGUI$Listener implements Listener {
    @EventHandler
    public void click(InventoryClickEvent clickEvent) {
        Player holder = (Player) clickEvent.getWhoClicked();
        if (!clickEvent.getView().getTitle().equals(PluginMessage.getTitle(holder, "g.langGUI.title"))) {
            return;
        }
        ItemStack I = clickEvent.getCurrentItem();
        if (!I.getItemMeta().hasCustomModelData()) {
            return;
        }
        switch (I.getItemMeta().getCustomModelData()) {
            case 436441:
                MiceCats.getPlugin().setLang(holder, "ru.yml");
                clickEvent.setCancelled(true);
                PluginMessage.MESSAGE(holder, "e.player_change_lang");
                break;
            case 436442:
                MiceCats.getPlugin().setLang(holder, "en.yml");
                clickEvent.setCancelled(true);
                PluginMessage.MESSAGE(holder, "e.player_change_lang");
                break;
        }
    }
}
