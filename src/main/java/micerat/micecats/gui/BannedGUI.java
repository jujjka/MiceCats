package micerat.micecats.gui;

import lombok.Getter;
import micerat.micecats.GameVoting;
import micerat.micecats.MiceCats;
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

public class BannedGUI implements InventoryHolder {
    private final Inventory gui;
    @Getter
    private Player holder;

    public BannedGUI(Player holder_pl) {
        this.holder = holder_pl;
        this.gui = Bukkit.createInventory(holder, 9, PluginMessage.getTitle(holder, "g.bannedItemsGUI.title"));

        loadItems();
        holder.openInventory(gui);
    }

    private void loadItems() {
        GameVoting gV = MiceCats.getVoting();
        gui.setItem(3, createGuiItem(
                Material.RED_BED,
                PluginMessage.getTitle(holder, "g.bannedItemsGUI.bed.name", String.valueOf(gV.getBed_bandVotes())),
                20081,
                PluginMessage.getTitle(holder, "g.bannedItemsGUI.bed.lore", String.valueOf(MiceCats.getGameSetting().TEAM_DAMAGE))));
        gui.setItem(4, createGuiItem(
                Material.POTION,
                PluginMessage.getTitle(holder, "g.bannedItemsGUI.potion.name", String.valueOf(gV.getPotion_effectVotes())),
                20082,
                PluginMessage.getTitle(holder, "g.bannedItemsGUI.potion.lore", String.valueOf(MiceCats.getGameSetting().POTION)))
        );
        gui.setItem(5, createGuiItem(
                Material.BARRIER,
                PluginMessage.getTitle(holder, "g.bannedItemsGUI.back.name"),
                20083
        ));
    }

    protected ItemStack createGuiItem(final Material material, final String name, final int model, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        meta.setCustomModelData(model);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);


        return item;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return gui;
    }

}