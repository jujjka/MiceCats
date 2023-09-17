package micerat.micecats.gui;

import lombok.Getter;
import micerat.micecats.cmds.PluginMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class LangGUI implements InventoryHolder {
    private final Inventory gui;
    @Getter
    private Player holder;

    public LangGUI(Player holder_pl) {
        this.holder = holder_pl;
        this.gui = Bukkit.createInventory(holder, 27, PluginMessage.getTitle(holder, "g.langGUI.title"));

        loadItems();
        holder.openInventory(gui);
    }

    private void loadItems() {
        gui.setItem(11, createGuiItem(Material.PAPER, PluginMessage.getTitle(holder, "g.langGUI.ru"), 436441));
        gui.setItem(15, createGuiItem(Material.PAPER, PluginMessage.getTitle(holder, "g.langGUI.en"), 436442));
    }

    protected ItemStack createGuiItem(final Material material, final String name, final int model, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        meta.setCustomModelData(model);
        item.setItemMeta(meta);


        return item;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return gui;
    }
}

