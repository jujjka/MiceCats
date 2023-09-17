package micerat.micecats.listeners.gui;

import micerat.micecats.GamePlayer;
import micerat.micecats.GameVoting;
import micerat.micecats.MiceCats;
import micerat.micecats.cmds.PluginMessage;
import micerat.micecats.gui.GameSettingsGUI;
import micerat.micecats.gui.LangGUI;
import micerat.micecats.utilis.LangUTIL;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Iterator;

public class BannedGUI$Listener implements Listener {
    @EventHandler
    public void click(InventoryClickEvent clickEvent){
        Player holder = (Player) clickEvent.getWhoClicked();
        if(!clickEvent.getView().getTitle().equals(PluginMessage.getTitle(holder,"g.bannedItemsGUI.title"))){return;}
        ItemStack I = clickEvent.getCurrentItem();
        if(!I.hasItemMeta()){return;}
        if(!I.getItemMeta().hasCustomModelData()){return;}
        int max_votes = MiceCats.getPluginConfig().getInt("game_setting.maxSelectionVote");
        GameVoting gM = MiceCats.getVoting();
        switch (I.getItemMeta().getCustomModelData()) {
            case 20081:
                clickEvent.setCancelled(true);
                if(gM.getPlayerBedBand().contains(holder)){
                    gM.setBed_bandVotes(gM.getBed_bandVotes() - 1);
                    gM.getPlayerBedBand().remove(holder);
                    gM.getPlayer_voting().remove(holder);
                    holder.closeInventory();
                    new GameSettingsGUI(holder);
                    PluginMessage.MESSAGE(holder,"e.player_cancelVoit");
                } else {
                    if (gM.getPlayer_voting().containsKey(holder)) {
                        if (gM.getPlayer_voting().get(holder) < max_votes) {
                            gM.setBed_bandVotes(gM.getBed_bandVotes() + 1);
                            gM.getPlayerBedBand().add(holder);
                            VoteCheck(holder, gM);
                            Iterator<GamePlayer> gpI = GamePlayer.getGame_players().listIterator();
                            while (gpI.hasNext()) {
                                GamePlayer gP = gpI.next();
                                PluginMessage.MESSAGE(gP.getPlayer(),
                                        "e.player_vote",
                                        holder.getName(),
                                        new LangUTIL().getString(MiceCats.getPlayers_lang().get(holder),
                                                        "g.bannedItemsGUI.bed.name").
                                                formatted(gM.getBed_bandVotes()));
                            }
                        } else {
                            PluginMessage.MESSAGE(holder, "e.player_max_vote");
                        }
                    } else {
                        gM.setBed_bandVotes(gM.getBed_bandVotes() + 1);
                        gM.getPlayerBedBand().add(holder);
                        gM.getPlayer_voting().put(holder, 1);
                        holder.closeInventory();
                        holder.playSound(holder, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1.0F, 1.0F);
                        new GameSettingsGUI(holder);
                        Iterator<GamePlayer> gpI = GamePlayer.getGame_players().listIterator();
                        while (gpI.hasNext()) {
                            GamePlayer gP = gpI.next();
                            PluginMessage.MESSAGE(gP.getPlayer(),
                                    "e.player_vote",
                                    holder.getName(),
                                    new LangUTIL().getString(MiceCats.getPlayers_lang().get(holder),
                                                    "g.bannedItemsGUI.bed.name").
                                            formatted(gM.getBed_bandVotes()));
                        }
                    }
                    break;
                }
            case 20082:
                clickEvent.setCancelled(true);
                if(gM.getPlayerPotion().contains(holder)){
                    gM.setPotion_effectVotes(gM.getPotion_effectVotes() - 1);
                    gM.getPlayerPotion().remove(holder);
                    gM.getPlayer_voting().remove(holder);
                    holder.closeInventory();
                    new GameSettingsGUI(holder);
                    PluginMessage.MESSAGE(holder,"e.player_cancelVoit");
                } else {
                    if (gM.getPlayer_voting().containsKey(holder)) {
                        if (gM.getPlayer_voting().get(holder) < max_votes) {
                            gM.setPotion_effectVotes(gM.getPotion_effectVotes() + 1);
                            gM.getPlayerPotion().add(holder);
                            VoteCheck(holder, gM);
                            Iterator<GamePlayer> gpI = GamePlayer.getGame_players().listIterator();
                            while (gpI.hasNext()) {
                                GamePlayer gP = gpI.next();
                                PluginMessage.MESSAGE(gP.getPlayer(),
                                        "e.player_vote",
                                        holder.getName(),
                                        new LangUTIL().getString(MiceCats.getPlayers_lang().get(holder),
                                                        "g.bannedItemsGUI.potion.name").
                                                formatted(gM.getPotion_effectVotes()));
                            }
                        } else {
                            PluginMessage.MESSAGE(holder, "e.player_max_vote");
                        }
                    } else {
                        gM.setPotion_effectVotes(gM.getPotion_effectVotes() + 1);
                        gM.getPlayerPotion().add(holder);
                        gM.getPlayer_voting().put(holder, 1);
                        holder.closeInventory();
                        holder.playSound(holder, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1.0F, 1.0F);
                        new GameSettingsGUI(holder);
                        Iterator<GamePlayer> gpI = GamePlayer.getGame_players().listIterator();
                        while (gpI.hasNext()) {
                            GamePlayer gP = gpI.next();
                            PluginMessage.MESSAGE(gP.getPlayer(),
                                    "e.player_vote",
                                    holder.getName(),
                                    new LangUTIL().getString(MiceCats.getPlayers_lang().get(holder),
                                                    "g.bannedItemsGUI.potion.name").
                                            formatted(gM.getPotion_effectVotes()));
                        }
                    }
                    break;
                }
            case 20083:
                clickEvent.setCancelled(true);
                holder.closeInventory();
                new GameSettingsGUI(holder);
                break;
        }
    }

    static void VoteCheck(Player holder, GameVoting gM) {
        if (gM.getPlayer_voting().containsKey(holder)) {
            int votes = gM.getPlayer_voting().get(holder);
            gM.getPlayer_voting().remove(holder);
            gM.getPlayer_voting().put(holder, votes);
        } else {
            gM.getPlayer_voting().put(holder, 1);
        }
        holder.closeInventory();
        holder.playSound(holder, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1.0F, 1.0F);
        new GameSettingsGUI(holder);
    }
}
