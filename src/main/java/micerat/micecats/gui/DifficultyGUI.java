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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class DifficultyGUI implements InventoryHolder {
    private final Inventory gui;
    @Getter
    private Player holder;

    public DifficultyGUI(Player holder_pl) {
        this.holder = holder_pl;
        this.gui = Bukkit.createInventory(holder, 9, PluginMessage.getTitle(holder, "g.difficultyGUI.title"));

        loadItems();
        holder.openInventory(gui);
    }

    private void loadItems() {
        GameVoting gV = MiceCats.getVoting();
        gui.setItem(1, createGuiItem(
                Material.WHITE_WOOL,
                PluginMessage.getTitle2(holder, "g.difficultyGUI.peaceful.name", String.valueOf(gV.getPeaceful_difficultyVotes())),
                12320030));
        gui.setItem(2, createGuiItem(
                Material.GREEN_WOOL,
                PluginMessage.getTitle2(holder, "g.difficultyGUI.easy.name", String.valueOf(gV.getEasy_difficultyVotes())),
                12320029
        ));
        gui.setItem(3, createGuiItem(
                Material.ORANGE_WOOL,
                PluginMessage.getTitle2(holder, "g.difficultyGUI.normal.name", String.valueOf(gV.getNormal_difficultyVotes()))
                ,
                12320028
        ));
        gui.setItem(4, createGuiItem(
                Material.RED_WOOL,
                PluginMessage.getTitle2(holder, "g.difficultyGUI.hard.name", String.valueOf(gV.getHard_difficultyVotes())),
                12320027
        ));
        gui.setItem(7, createGuiItem(
                Material.PAPER,
                PluginMessage.getTitle2(holder, "g.difficultyGUI.info", MiceCats.getGameSetting().getDIFFICULTY().name()),
                12320026));
        gui.setItem(8, createGuiItem(
                Material.BARRIER,
                PluginMessage.getTitle(holder, "g.difficultyGUI.back.name"),
                12320025
        ));
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
