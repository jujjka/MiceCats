package micerat.micecats.listeners.gui;

import micerat.micecats.GamePlayer;
import micerat.micecats.GameVoting;
import micerat.micecats.MiceCats;
import micerat.micecats.cmds.PluginMessage;
import micerat.micecats.gui.GameSettingsGUI;
import micerat.micecats.utilis.LangUTIL;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class DifficultyGUI$Listener implements Listener {
    @EventHandler
    public void click(InventoryClickEvent clickEvent) {
        Player holder = (Player) clickEvent.getWhoClicked();
        if (!clickEvent.getView().getTitle().equals(PluginMessage.getTitle(holder, "g.difficultyGUI.title"))) {
            return;
        }
        ItemStack I = clickEvent.getCurrentItem();
        if (!I.getItemMeta().hasCustomModelData()) {
            return;
        }
        int max_votes = MiceCats.getPluginConfig().getInt("game_setting.maxSelectionVote");
        GameVoting gM = MiceCats.getVoting();
        clickEvent.setCancelled(true);
        switch (I.getItemMeta().getCustomModelData()) {
            case 12320030:
                clickEvent.setCancelled(true);
                if (gM.getPlayerPeaceful().contains(holder)) {
                    gM.setPeaceful_difficultyVotes(gM.getPeaceful_difficultyVotes() - 1);
                    gM.getPlayerPeaceful().remove(holder);
                    gM.getPlayer_voting().remove(holder);
                    holder.closeInventory();
                    new GameSettingsGUI(holder);
                    PluginMessage.MESSAGE(holder, "e.player_cancelVoit");
                } else {
                    if (gM.getPlayer_voting().containsKey(holder)) {
                        if (gM.getPlayer_voting().get(holder) < max_votes) {
                            gM.setPeaceful_difficultyVotes(gM.getPeaceful_difficultyVotes() + 1);
                            gM.getPlayerPeaceful().add(holder);
                            BannedGUI$Listener.VoteCheck(holder, gM);
                            Iterator<GamePlayer> gpI = GamePlayer.getGame_players().listIterator();
                            while (gpI.hasNext()) {
                                GamePlayer gP = gpI.next();
                                PluginMessage.MESSAGE(gP.getPlayer(),
                                        "e.player_vote",
                                        holder.getName(),
                                        new LangUTIL().getString(MiceCats.getPlayers_lang().get(holder),
                                                        "g.difficultyGUI.peaceful.name").
                                                formatted(gM.getPeaceful_difficultyVotes()));
                            }
                        } else {
                            PluginMessage.MESSAGE(holder, "e.player_max_vote");
                        }
                    } else {
                        gM.setPeaceful_difficultyVotes(gM.getPeaceful_difficultyVotes() + 1);
                        gM.getPlayerPeaceful().add(holder);
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
                                                    "g.difficultyGUI.peaceful.name").
                                            formatted(gM.getPeaceful_difficultyVotes()));
                        }
                    }
                    break;
                }
            case 12320029:
                clickEvent.setCancelled(true);
                if (gM.getPlayerEasy().contains(holder)) {
                    gM.setEasy_difficultyVotes(gM.getEasy_difficultyVotes() - 1);
                    gM.getPlayerEasy().remove(holder);
                    gM.getPlayer_voting().remove(holder);
                    holder.closeInventory();
                    new GameSettingsGUI(holder);
                    PluginMessage.MESSAGE(holder, "e.player_cancelVoit");
                } else {
                    if (gM.getPlayer_voting().containsKey(holder)) {
                        if (gM.getPlayer_voting().get(holder) < max_votes) {
                            gM.setEasy_difficultyVotes(gM.getEasy_difficultyVotes() + 1);
                            gM.getPlayerEasy().add(holder);
                            BannedGUI$Listener.VoteCheck(holder, gM);
                            Iterator<GamePlayer> gpI = GamePlayer.getGame_players().listIterator();
                            while (gpI.hasNext()) {
                                GamePlayer gP = gpI.next();
                                PluginMessage.MESSAGE(gP.getPlayer(),
                                        "e.player_vote",
                                        holder.getName(),
                                        new LangUTIL().getString(MiceCats.getPlayers_lang().get(holder),
                                                        "g.difficultyGUI.easy.name").
                                                formatted(gM.getEasy_difficultyVotes()));
                            }
                        } else {
                            PluginMessage.MESSAGE(holder, "e.player_max_vote");
                        }
                    } else {
                        gM.setEasy_difficultyVotes(gM.getEasy_difficultyVotes() + 1);
                        gM.getPlayerEasy().add(holder);
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
                                                    "g.difficultyGUI.easy.name").
                                            formatted(gM.getEasy_difficultyVotes()));
                        }
                    }
                    break;
                }
            case 12320028:
                clickEvent.setCancelled(true);
                if (gM.getPlayerNormal().contains(holder)) {
                    gM.setNormal_difficultyVotes(gM.getNormal_difficultyVotes() - 1);
                    gM.getPlayerNormal().remove(holder);
                    gM.getPlayer_voting().remove(holder);
                    holder.closeInventory();
                    new GameSettingsGUI(holder);
                    PluginMessage.MESSAGE(holder, "e.player_cancelVoit");
                } else {
                    if (gM.getPlayer_voting().containsKey(holder)) {
                        if (gM.getPlayer_voting().get(holder) < max_votes) {
                            gM.setNormal_difficultyVotes(gM.getNormal_difficultyVotes() + 1);
                            gM.getPlayerNormal().add(holder);
                            BannedGUI$Listener.VoteCheck(holder, gM);
                            Iterator<GamePlayer> gpI = GamePlayer.getGame_players().listIterator();
                            while (gpI.hasNext()) {
                                GamePlayer gP = gpI.next();
                                PluginMessage.MESSAGE(gP.getPlayer(),
                                        "e.player_vote",
                                        holder.getName(),
                                        new LangUTIL().getString(MiceCats.getPlayers_lang().get(holder),
                                                        "g.difficultyGUI.normal.name").
                                                formatted(gM.getNormal_difficultyVotes()));
                            }
                        } else {
                            PluginMessage.MESSAGE(holder, "e.player_max_vote");
                        }
                    } else {
                        gM.setNormal_difficultyVotes(gM.getNormal_difficultyVotes() + 1);
                        gM.getPlayerNormal().add(holder);
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
                                                    "g.difficultyGUI.normal.name").
                                            formatted(gM.getNormal_difficultyVotes()));
                        }
                    }
                    break;
                }
            case 12320027:
                clickEvent.setCancelled(true);
                if (gM.getPlayerHard().contains(holder)) {
                    gM.setHard_difficultyVotes(gM.getHard_difficultyVotes() - 1);
                    gM.getPlayerHard().remove(holder);
                    gM.getPlayer_voting().remove(holder);
                    holder.closeInventory();
                    new GameSettingsGUI(holder);
                    PluginMessage.MESSAGE(holder, "e.player_cancelVoit");
                } else {
                    if (gM.getPlayer_voting().containsKey(holder)) {
                        if (gM.getPlayer_voting().get(holder) < max_votes) {
                            gM.setHard_difficultyVotes(gM.getHard_difficultyVotes() + 1);
                            gM.getPlayerHard().add(holder);
                            BannedGUI$Listener.VoteCheck(holder, gM);
                            Iterator<GamePlayer> gpI = GamePlayer.getGame_players().listIterator();
                            while (gpI.hasNext()) {
                                GamePlayer gP = gpI.next();
                                PluginMessage.MESSAGE(gP.getPlayer(),
                                        "e.player_vote",
                                        holder.getName(),
                                        new LangUTIL().getString(MiceCats.getPlayers_lang().get(holder),
                                                        "g.difficultyGUI.hard.name").
                                                formatted(gM.getHard_difficultyVotes()));
                            }
                        } else {
                            PluginMessage.MESSAGE(holder, "e.player_max_vote");
                        }
                    } else {
                        gM.setHard_difficultyVotes(gM.getHard_difficultyVotes() + 1);
                        gM.getPlayerHard().add(holder);
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
                                                    "g.difficultyGUI.hard.name").
                                            formatted(gM.getHard_difficultyVotes()));
                        }
                    }
                    break;
                }
            case 12320025:
                clickEvent.setCancelled(true);
                new GameSettingsGUI(holder);
                break;
            case 12320026:
                clickEvent.setCancelled(true);
                break;
        }
    }
}
