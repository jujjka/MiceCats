package micerat.micecats.listeners.gui;

import micerat.micecats.cmds.PluginMessage;
import micerat.micecats.gui.LangGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PersonalSettingsGUI$Listener implements Listener {
    @EventHandler
    public void click(InventoryClickEvent clickEvent) {
        Player holder = (Player) clickEvent.getWhoClicked();
        if (!clickEvent.getView().getTitle().equals(PluginMessage.getTitle(holder, "g.settingGUI.title"))) {
            return;
        }
        ItemStack I = clickEvent.getCurrentItem();
        if (!I.hasItemMeta()) {
            return;
        }
        if (!I.getItemMeta().hasCustomModelData()) {
            return;
        }
        switch (I.getItemMeta().getCustomModelData()) {
            case 436431:
                PotionEffect potionEffect = holder.getPotionEffect(PotionEffectType.NIGHT_VISION);
                if (potionEffect == null) {
                    holder.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 0, false, false));
                    PluginMessage.MESSAGE(holder, "e.player_gamma_on");
                } else {
                    holder.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    PluginMessage.MESSAGE(holder, "e.player_gamma_of");
                }
                clickEvent.setCancelled(true);
                break;
            case 436432:
                new LangGUI(holder);
                clickEvent.setCancelled(true);
                break;
        }
    }
}
