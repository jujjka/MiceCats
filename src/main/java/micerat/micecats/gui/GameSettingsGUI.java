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

public class GameSettingsGUI implements InventoryHolder {
    private final Inventory gui;
    @Getter
    private Player holder;

    public GameSettingsGUI(Player holder_pl) {
        this.holder = holder_pl;
        this.gui = Bukkit.createInventory(holder, 27, PluginMessage.getTitle(holder, "g.game-settingsGUI.title"));

        loadItems();
        holder.openInventory(gui);
    }

    private void loadItems() {
        GameVoting gV = MiceCats.getVoting();
        gui.setItem(10, createGuiItem(
                Material.DIAMOND_SWORD,
                PluginMessage.getTitle2(holder, "g.game-settingsGUI.team_damage.name", String.valueOf(gV.getTeam_damageVotes())),
                100030,
                PluginMessage.getTitle2(holder, "g.game-settingsGUI.team_damage.lore", String.valueOf(MiceCats.getGameSetting().TEAM_DAMAGE))));
        gui.setItem(11, createGuiItem(
                Material.BARRIER,
                PluginMessage.getTitle(holder, "g.game-settingsGUI.ban_items.name"),
                100029
        ));
        gui.setItem(12, createGuiItem(
                Material.GOLDEN_APPLE,
                PluginMessage.getTitle2(holder, "g.game-settingsGUI.death_runner_made_hunter.name", String.valueOf(gV.getDeath_runnerVotes())),
                100028,
                PluginMessage.getTitle(holder, "g.game-settingsGUI.death_runner_made_hunter.lore", String.valueOf(MiceCats.getGameSetting().DEATH_RUNNERS))
        ));
        gui.setItem(13, createGuiItem(
                Material.LEGACY_WATCH,
                PluginMessage.getTitle2(holder, "g.game-settingsGUI.latersMadeHunter.name", String.valueOf(gV.getLaterVotes())),
                100027,
                PluginMessage.getTitle2(holder, "g.game-settingsGUI.latersMadeHunter.lore", String.valueOf(MiceCats.getGameSetting().LATERS_PLAYER))

        ));
        gui.setItem(14, createGuiItem(
                Material.END_PORTAL_FRAME,
                PluginMessage.getTitle2(holder, "g.game-settingsGUI.netherPassFor-hunter.name", String.valueOf(gV.getNetherPassVotes())),
                100026,
                PluginMessage.getTitle2(holder, "g.game-settingsGUI.netherPassFor-hunter.lore", String.valueOf(MiceCats.getGameSetting().NETHER_PASS))
        ));
        gui.setItem(15, createGuiItem(
                Material.PURPLE_CONCRETE,
                PluginMessage.getTitle(holder, "g.game-settingsGUI.difficulty.name"),
                100025)
        );
        gui.setItem(16, createGuiItem(
                Material.ENDER_CHEST,
                PluginMessage.getTitle2(holder, "g.game-settingsGUI.save_inventory.name", String.valueOf(gV.getSave_inventoryVotes())),
                100024,
                PluginMessage.getTitle2(holder, "g.game-settingsGUI.save_inventory.lore", String.valueOf(MiceCats.getGameSetting().isSAVE_INVENTORY())
                )));
        gui.setItem(7, createGuiItem(
                Material.WRITTEN_BOOK,
                PluginMessage.getTitle(holder, "g.game-settingsGUI.description.name"),
                100023,
                PluginMessage.getTitle(holder, "g.game-settingsGUI.description.lore")
        ));
    }

    protected ItemStack createGuiItem(final Material material, final String name, final int model, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        meta.setCustomModelData(model);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);


        return item;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return gui;
    }

}


