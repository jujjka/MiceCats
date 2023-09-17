package micerat.micecats.listeners.gui;

import micerat.micecats.GamePlayer;
import micerat.micecats.GameVoting;
import micerat.micecats.MiceCats;
import micerat.micecats.cmds.PluginMessage;
import micerat.micecats.gui.BannedGUI;
import micerat.micecats.gui.DifficultyGUI;
import micerat.micecats.gui.GameSettingsGUI;
import micerat.micecats.utilis.LangUTIL;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class GameSettingsGUI$Listener implements Listener {
    @EventHandler
    public void click(InventoryClickEvent clickEvent) {
        Player holder = (Player) clickEvent.getWhoClicked();
        if (!clickEvent.getView().getTitle().equals(PluginMessage.getTitle(holder, "g.game-settingsGUI.title"))) {
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
        int max_votes = MiceCats.getPluginConfig().getInt("game_setting.maxSelectionVote");
        GameVoting gM = MiceCats.getVoting();
        switch (I.getItemMeta().getCustomModelData()) {
            case 100030:
                clickEvent.setCancelled(true);
                if (gM.getPlayerTeamDamage().contains(holder)) {
                    gM.setTeam_damageVotes(gM.getTeam_damageVotes() - 1);
                    gM.getPlayerTeamDamage().remove(holder);
                    gM.getPlayer_voting().remove(holder);
                    holder.closeInventory();
                    new GameSettingsGUI(holder);
                    PluginMessage.MESSAGE(holder, "e.player_cancelVoit");
                } else {
                    if (gM.getPlayer_voting().containsKey(holder)) {
                        if (gM.getPlayer_voting().get(holder) < max_votes) {
                            gM.setTeam_damageVotes(gM.getTeam_damageVotes() + 1);
                            gM.getPlayerTeamDamage().add(holder);
                            if (gM.getPlayer_voting().containsKey(holder)) {
                                int votes = gM.getPlayer_voting().get(holder);
                                gM.getPlayer_voting().remove(holder);
                                gM.getPlayer_voting().put(holder, votes);
                            } else {
                                gM.getPlayer_voting().put(holder, 1);
                            }
                            holder.closeInventory();
                            new GameSettingsGUI(holder);
                            holder.playSound(holder, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1.0F, 1.0F);
                            Iterator<GamePlayer> gpI = GamePlayer.getGame_players().listIterator();
                            while (gpI.hasNext()) {
                                GamePlayer gP = gpI.next();
                                PluginMessage.MESSAGE(gP.getPlayer(),
                                        "e.player_vote",
                                        holder.getName(),
                                        new LangUTIL().getString(MiceCats.getPlayers_lang().get(holder),
                                                        "g.game-settingsGUI.team_damage.name").
                                                formatted(gM.getTeam_damageVotes()));
                            }
                        } else {
                            PluginMessage.MESSAGE(holder, "e.player_max_vote");
                        }
                    } else {
                        gM.setTeam_damageVotes(gM.getTeam_damageVotes() + 1);
                        gM.getPlayerTeamDamage().add(holder);
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
                                                    "g.game-settingsGUI.team_damage.name").
                                            formatted(gM.getTeam_damageVotes()));
                        }
                    }
                }
                break;
            case 100029:
                new BannedGUI(holder);
                clickEvent.setCancelled(true);
                break;
            case 100028:
                clickEvent.setCancelled(true);
                if (gM.getPlayerDeathRunner().contains(holder)) {
                    gM.setDeath_runnerVotes(gM.getDeath_runnerVotes() - 1);
                    gM.getPlayerDeathRunner().remove(holder);
                    gM.getPlayer_voting().remove(holder);
                    holder.closeInventory();
                    new GameSettingsGUI(holder);
                    PluginMessage.MESSAGE(holder, "e.player_cancelVoit");
                } else {
                    if (gM.getPlayer_voting().containsKey(holder)) {
                        if (gM.getPlayer_voting().get(holder) < max_votes) {
                            gM.setDeath_runnerVotes(gM.getDeath_runnerVotes() + 1);
                            gM.getPlayerDeathRunner().add(holder);
                            VoteCheck(holder, gM);
                            Iterator<GamePlayer> gpI = GamePlayer.getGame_players().listIterator();
                            while (gpI.hasNext()) {
                                GamePlayer gP = gpI.next();
                                PluginMessage.MESSAGE(gP.getPlayer(),
                                        "e.player_vote",
                                        holder.getName(),
                                        new LangUTIL().getString(MiceCats.getPlayers_lang().get(holder),
                                                        "g.game-settingsGUI.death_runner_made_hunter.name").
                                                formatted(gM.getDeath_runnerVotes()));
                            }
                        } else {
                            PluginMessage.MESSAGE(holder, "e.player_max_vote");
                        }
                    } else {
                        gM.setDeath_runnerVotes(gM.getDeath_runnerVotes() + 1);
                        gM.getPlayerDeathRunner().add(holder);
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
                                                    "g.game-settingsGUI.death_runner_made_hunter.name").
                                            formatted(gM.getDeath_runnerVotes()));
                        }
                    }
                }
                break;
            case 100027:
                clickEvent.setCancelled(true);
                if (gM.getPlayerlaterVotes().contains(holder)) {
                    gM.setLaterVotes(gM.getLaterVotes() - 1);
                    gM.getPlayerlaterVotes().remove(holder);
                    gM.getPlayer_voting().remove(holder);
                    holder.closeInventory();
                    new GameSettingsGUI(holder);
                    PluginMessage.MESSAGE(holder, "e.player_cancelVoit");
                } else {
                    if (gM.getPlayer_voting().containsKey(holder)) {
                        if (gM.getPlayer_voting().get(holder) < max_votes) {
                            gM.setLaterVotes(gM.getLaterVotes() + 1);
                            gM.getPlayerlaterVotes().add(holder);
                            VoteCheck(holder, gM);
                            Iterator<GamePlayer> gpI = GamePlayer.getGame_players().listIterator();
                            while (gpI.hasNext()) {
                                GamePlayer gP = gpI.next();
                                PluginMessage.MESSAGE(gP.getPlayer(),
                                        "e.player_vote",
                                        holder.getName(),
                                        new LangUTIL().getString(MiceCats.getPlayers_lang().get(holder),
                                                        "g.game-settingsGUI.latersMadeHunter.name").
                                                formatted(gM.getLaterVotes()));
                            }
                        } else {
                            PluginMessage.MESSAGE(holder, "e.player_max_vote");
                        }
                    } else {
                        gM.setLaterVotes(gM.getLaterVotes() + 1);
                        gM.getPlayerlaterVotes().add(holder);
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
                                                    "g.game-settingsGUI.latersMadeHunter.name").
                                            formatted(gM.getLaterVotes()));
                        }
                    }
                }
                break;
            case 100026:
                clickEvent.setCancelled(true);
                if (gM.getPlayerNetherPass().contains(holder)) {
                    gM.setNetherPassVotes(gM.getNetherPassVotes() - 1);
                    gM.getPlayerNetherPass().remove(holder);
                    gM.getPlayer_voting().remove(holder);
                    holder.closeInventory();
                    new GameSettingsGUI(holder);
                    PluginMessage.MESSAGE(holder, "e.player_cancelVoit");
                } else {
                    if (gM.getPlayer_voting().containsKey(holder)) {
                        if (gM.getPlayer_voting().get(holder) < max_votes) {
                            gM.setNetherPassVotes(gM.getNetherPassVotes() + 1);
                            gM.getPlayerNetherPass().add(holder);
                            VoteCheck(holder, gM);
                            Iterator<GamePlayer> gpI = GamePlayer.getGame_players().listIterator();
                            while (gpI.hasNext()) {
                                GamePlayer gP = gpI.next();
                                PluginMessage.MESSAGE(gP.getPlayer(),
                                        "e.player_vote",
                                        holder.getName(),
                                        new LangUTIL().getString(MiceCats.getPlayers_lang().get(holder),
                                                        "g.game-settingsGUI.netherPassFor-hunter.name").
                                                formatted(gM.getNetherPassVotes()));
                            }
                        } else {
                            PluginMessage.MESSAGE(holder, "e.player_max_vote");
                        }
                    } else {
                        gM.setNetherPassVotes(gM.getNetherPassVotes() + 1);
                        gM.getPlayerNetherPass().add(holder);
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
                                                    "g.game-settingsGUI.netherPassFor-hunter.name").
                                            formatted(gM.getNetherPassVotes()));
                        }
                    }
                }
                break;
            case 100025:
                new DifficultyGUI(holder);
                clickEvent.setCancelled(true);
                break;
            case 100024:
                clickEvent.setCancelled(true);
                if (gM.getPlayerSaveInventory().contains(holder)) {
                    gM.setSave_inventoryVotes(gM.getSave_inventoryVotes() - 1);
                    gM.getPlayerSaveInventory().remove(holder);
                    gM.getPlayer_voting().remove(holder);
                    holder.closeInventory();
                    new GameSettingsGUI(holder);
                    PluginMessage.MESSAGE(holder, "e.player_cancelVoit");
                } else {
                    if (gM.getPlayer_voting().containsKey(holder)) {
                        if (gM.getPlayer_voting().get(holder) < max_votes) {
                            gM.setSave_inventoryVotes(gM.getSave_inventoryVotes() + 1);
                            gM.getPlayerSaveInventory().add(holder);
                            VoteCheck(holder, gM);
                            Iterator<GamePlayer> gpI = GamePlayer.getGame_players().listIterator();
                            while (gpI.hasNext()) {
                                GamePlayer gP = gpI.next();
                                PluginMessage.MESSAGE(gP.getPlayer(),
                                        "e.player_vote",
                                        holder.getName(),
                                        new LangUTIL().getString(MiceCats.getPlayers_lang().get(holder),
                                                        "g.game-settingsGUI.save_inventory.name").
                                                formatted(gM.getSave_inventoryVotes()));
                            }
                        } else {
                            PluginMessage.MESSAGE(holder, "e.player_max_vote");
                        }
                    } else {
                        gM.setSave_inventoryVotes(gM.getSave_inventoryVotes() + 1);
                        gM.getPlayerSaveInventory().add(holder);
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
                                                    "g.game-settingsGUI.save_inventory.name").
                                            formatted(gM.getSave_inventoryVotes()));
                        }
                    }
                    break;
                }
            case 100023:
                clickEvent.setCancelled(true);
                break;
        }
    }

    private void VoteCheck(Player holder, GameVoting gM) {
        if (gM.getPlayer_voting().containsKey(holder)) {
            int votes = gM.getPlayer_voting().get(holder);
            gM.getPlayer_voting().remove(holder);
            gM.getPlayer_voting().put(holder, votes + 1);
        } else {
            gM.getPlayer_voting().put(holder, 1);
        }
        holder.closeInventory();
        holder.playSound(holder, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1.0F, 1.0F);
        new GameSettingsGUI(holder);
    }
}
