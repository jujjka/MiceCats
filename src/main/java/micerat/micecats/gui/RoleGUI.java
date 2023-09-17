package micerat.micecats.gui;

import lombok.Getter;
import micerat.micecats.cmds.PluginMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class RoleGUI implements InventoryHolder {
    private final Inventory gui;
    @Getter
    private Player holder;

    public RoleGUI(Player holder_pl) {
        this.holder = holder_pl;
        this.gui = Bukkit.createInventory(holder, 45, PluginMessage.getTitle(holder, "g.roleGUI.title"));

        loadItems();
        holder.openInventory(gui);
    }

    private void loadItems() {
        gui.setItem(19, createGuiItem(Material.NETHERITE_SWORD, PluginMessage.getTitle(holder, "g.roleGUI.hunter"), 500000));
        gui.setItem(22, createGuiItem(Material.DIAMOND_BOOTS, PluginMessage.getTitle(holder, "g.roleGUI.runner"), 500001));
        gui.setItem(25, createGuiItem(Material.ELYTRA, PluginMessage.getTitle(holder, "g.roleGUI.spectator"), 500002));
    }

    protected ItemStack createGuiItem(final Material material, final String name, final int model, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        meta.setCustomModelData(model);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);


        return item;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return gui;
    }
}


