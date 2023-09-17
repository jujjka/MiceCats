package micerat.micecats.listeners.user;

import micerat.micecats.GameManager;
import micerat.micecats.GamePlayer;
import micerat.micecats.MiceCats;
import micerat.micecats.cmds.PluginMessage;
import micerat.micecats.gui.CompassGUI;
import micerat.micecats.gui.GameSettingsGUI;
import micerat.micecats.gui.RoleGUI;
import micerat.micecats.gui.SettingsGUI;
import micerat.micecats.utilis.WorldUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class Player$Listener implements Listener {
    @EventHandler
    public void userJoin(PlayerJoinEvent joinEvent) {
        Player user = joinEvent.getPlayer();
        String dL = MiceCats.getPlugin().getDefault_lang();
        String lang_set = MiceCats.getPluginConfig().getString("users." + user.getName());
        if (lang_set == null) {
            MiceCats.getPlugin().setLang(user, dL);
        } else {
            MiceCats.getPlugin().setLang(user, lang_set);
        }
        if (!MiceCats.isIs_Game()) {
            user.setGameMode(GameMode.ADVENTURE);
            user.getInventory().clear();
            user.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Minecraft Manhunt");
            user.sendMessage("");
            user.teleport(Bukkit.getWorld(new WorldUtil().default_world_name()).getSpawnLocation());
            PluginMessage.MESSAGE(user, "e.game_soon_start");
            PluginMessage.MESSAGE(user, "e.give_role");
            new PlayerHelper().setupItems(user);
        }
        if (MiceCats.isIs_Game() && GamePlayer.getGame_player(user) == null) {
            user.getInventory().clear();
            user.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Minecraft Manhunt");
            user.sendMessage("");
            user.setGameMode(GameMode.ADVENTURE);
            user.teleport(Bukkit.getWorld(new WorldUtil().default_world_name()).getSpawnLocation());
            PluginMessage.MESSAGE(user, "e.game_start_soon");
            PluginMessage.MESSAGE(user, "e.give_role");
            new PlayerHelper().setupItems(user);

        }
    }

    @EventHandler
    public void useIt(PlayerInteractEvent interactEvent) {
        Player user = interactEvent.getPlayer();
        Action action = interactEvent.getAction();
        if (!action.equals(Action.RIGHT_CLICK_AIR) && !action.equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        ItemStack iS = user.getItemInHand();
        if (!iS.hasItemMeta()) {
            return;
        }
        if (!iS.getItemMeta().hasCustomModelData()) {
            return;
        }
        if (!MiceCats.isIs_ChunksReload()) {
            switch (iS.getItemMeta().getCustomModelData()) {
                case 44444:
                    if (!MiceCats.isIs_Game()) {
                        if (MiceCats.getTeamMice().size() >= MiceCats.getMinR() &&
                                MiceCats.getTeamCat().size() >= MiceCats.getMinH()) {
                            if (!MiceCats.isTimer()) {
                                MiceCats.getGameManager().messageForTeams("e.timerStart");
                                MiceCats.getGameManager().timer();
                            } else {
                                MiceCats.setTimer(false);
                                MiceCats.getGameManager().messageForTeams("e.timerCancel");
                            }
                        } else {
                            PluginMessage.MESSAGE(user, "e.game_many_players");
                        }
                    }
                    break;
                case 44445:
                    if (!MiceCats.isIs_Game()) {
                        new RoleGUI(user);
                    } else {
                        PluginMessage.MESSAGE(user, "e.player_dont_open");
                    }
                    break;
                case 44446:
                    if (GamePlayer.getGame_player(user) == null) {
                        PluginMessage.MESSAGE(user, "e.player_not_user");
                        return;
                    }
                    GamePlayer gP = GamePlayer.getGame_player(user);
                    assert gP != null;
                    gP.setReady(!gP.isReady());
                    if (gP.isReady()) {
                        user.getInventory().setItem(4, new PlayerHelper().createGuiItem(Material.RED_CONCRETE, PluginMessage.getTitle(user, "i.ready"), 44446, false));
                    } else {
                        user.getInventory().setItem(4, new PlayerHelper().createGuiItem(Material.GREEN_CONCRETE, PluginMessage.getTitle(user, "i.ready"), 44446, false));
                    }
                    break;
                case 44447:
                    new SettingsGUI(user);
                    break;
                case 20039:
                    if (!MiceCats.isIs_Game()) {
                        new GameSettingsGUI(user);
                    }
                    break;
                case 397122:
                    if (GamePlayer.getGame_player(user) == null) {
                        return;
                    }
                    if (!MiceCats.isIs_Game()) {
                        return;
                    }
                    if (GamePlayer.getGame_player(user).getRole() == GamePlayer.Role.CAT) {
                        new CompassGUI(user);
                    }
                    break;
                case 397123:
                    if(GamePlayer.getGame_player(user) == null){return;}
                    GamePlayer g2P = GamePlayer.getGame_player(user);
                    if(g2P.getTarget() != null){
                        int t = -1;
                        int slot = -1;
                        for (ItemStack i: user.getInventory().getContents()){
                            t++;
                            if(i != null){
                                if(i.getType() == Material.COMPASS){
                                    slot = t;
                                }
                            }
                        }
                        new PlayerHelper().giveCompass(user, g2P.getTarget(),slot);
                    }
                    break;
            }
        } else {
            PluginMessage.MESSAGE(user, "e.worldChunkNow");
            interactEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void userMove(PlayerMoveEvent moveEvent) {
        Player user = moveEvent.getPlayer();
        if (GamePlayer.getGame_player(user) == null) {
            return;
        }
        if (!MiceCats.isIs_Game()) {
            return;
        }
        if (MiceCats.isPaused()) {
            moveEvent.setCancelled(true);
        }
    }

//    @EventHandler
//    public void userDeath(PlayerDeathEvent deathEvent) {
//        Player user = deathEvent.getPlayer();
//        if (GamePlayer.getGame_player(user) != null) {
//            if (!MiceCats.isIs_Game()) {
//                return;
//            }
//            GamePlayer gP = GamePlayer.getGame_player(user);
//
//            if (gP.getRole().equals(GamePlayer.Role.MICE)) {
//                MiceCats.getGameManager().deletePlayer(gP, false, true);
//
//                if (user.getGameMode() == GameMode.SPECTATOR) {
//                    user.setGameMode(GameMode.SURVIVAL);
//                }
//
//                deathEvent.setDeathMessage("");
//                if (MiceCats.getTeamMice().size() == 0) {
//                    if (MiceCats.getWinTeam() == null) {
//                        MiceCats.getGameManager().HunterWin();
//                    }
//                }
//            }
//        }
//    }

    @EventHandler
    public void userRespawn(PlayerRespawnEvent respawnEvent) {
        Player user = respawnEvent.getPlayer();
        if (GamePlayer.getGame_player(user) != null) {
            GamePlayer gP = GamePlayer.getGame_player(user);
                respawnEvent.setRespawnLocation(Bukkit.getWorld(new WorldUtil().world_name()).getSpawnLocation());
        } else {
            new PlayerHelper().setupItems(user);
            user.getInventory().remove(Material.RED_CONCRETE);
        }
    }

    @EventHandler
    public void userDrop(PlayerDropItemEvent dropEvent) {
        Player user = dropEvent.getPlayer();
        ItemStack i = dropEvent.getItemDrop().getItemStack();
        if (MiceCats.isIs_Game()) {
            return;
        }
        if (!user.getWorld().getName().equals(new WorldUtil().default_world_name())) {
            return;
        }
        if (i.getType() == Material.RED_CONCRETE) {
            dropEvent.setCancelled(true);
        }
        if (i.getType() == Material.FEATHER) {
            dropEvent.setCancelled(true);
        }
        if (i.hasItemMeta()) {
            if (i.getItemMeta().hasCustomModelData()) {
                dropEvent.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void pickDrop(PlayerPickupItemEvent pickupEvent) {
        Player user = pickupEvent.getPlayer();
        if (GamePlayer.getGame_player(user) == null) {
            return;
        }
        if (!MiceCats.isIs_Game()) {
            return;
        }
        GamePlayer gP = GamePlayer.getGame_player(user);
        ItemStack i = pickupEvent.getItem().getItemStack();
        if (!i.hasItemMeta()) {
            return;
        }
        if (!i.getItemMeta().hasCustomModelData()) {
            return;
        }
        if (i.getItemMeta().getCustomModelData() != 397122) {
            return;
        }
        if (gP.getRole() != GamePlayer.Role.CAT) {
            pickupEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void userDamage(EntityDamageByEntityEvent damageEvent) {
        if (!MiceCats.isIs_Game()) {
            return;
        }
        if (!(damageEvent.getDamager() instanceof Player damager)) {
            return;
        }
        if (!(damageEvent.getEntity() instanceof Player target)) {
            return;
        }
        if (GamePlayer.getGame_player(damager) == null) {
            return;
        }
        if (GamePlayer.getGame_player(target) == null) {
            return;
        }
        GamePlayer gpDamage = GamePlayer.getGame_player(damager);
        GamePlayer gpTarget = GamePlayer.getGame_player(target);
        assert gpTarget != null;
        if (!gpDamage.getRole().equals(gpTarget.getRole())) {
            return;
        }
        if (MiceCats.getGameSetting().isTEAM_DAMAGE()) {
            return;
        }
        damageEvent.setCancelled(true);
    }

    @EventHandler
    public void userPotion(PlayerItemConsumeEvent consumeEvent) {
        if (!MiceCats.isIs_Game()) {
            return;
        }
        if (GamePlayer.getGame_player(consumeEvent.getPlayer()) == null) {
            return;
        }
        if (MiceCats.getGameSetting().isPOTION()) {
            return;
        }
        ItemStack consumed = consumeEvent.getItem();
        if (!consumed.getType().equals(Material.POTION)) {
            return;
        }
        Potion potion = Potion.fromItemStack(consumed);
        if (potion.getType().name().contains("STRENGTH") || potion.getType().equals(PotionType.STRENGTH)) {
            consumeEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void userPotion(PlayerInteractEvent interactEvent) {
        if (interactEvent.getAction() != Action.RIGHT_CLICK_AIR && interactEvent.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        Player user = interactEvent.getPlayer();
        if (!interactEvent.hasItem()) {
            return;
        }
        if (interactEvent.getItem().getType() != Material.POTION) {
            return;
        }
        ItemStack cons = interactEvent.getItem();
        Potion potion = Potion.fromItemStack(cons);
        if (!potion.getType().name().contains("STRENGTH")) {
            return;
        }
        if (!MiceCats.Is_Game) {
            return;
        }
        if (GamePlayer.getGame_player(interactEvent.getPlayer()) == null) {
            return;
        }
        if (MiceCats.getGameSetting().isPOTION()) {
            return;
        }
        interactEvent.setCancelled(true);
    }

    @EventHandler
    public void userPortal(PlayerPortalEvent portalEvent) {
        if (!MiceCats.Is_Game) {
            return;
        }
        World world = portalEvent.getTo().getWorld();
        if (world.getEnvironment() != World.Environment.NETHER) {
            return;
        }
        if (!portalEvent.getFrom().getWorld().getName().equals(new WorldUtil().world_name())) {
            return;
        }
        Player user = portalEvent.getPlayer();
        if (GamePlayer.getGame_player(user) == null) {
            return;
        }
        GamePlayer gP = GamePlayer.getGame_player(user);
        switch (gP.getRole()) {
            case CAT:
                if (!MiceCats.isNetherActivated()) {
                    if (MiceCats.getGameSetting().isNETHER_PASS()) {
                        gP.setDEFOLT_WORLD_LOCATION(portalEvent.getFrom().add(-1, 0, 0));
                        Location location = portalEvent.getFrom();
                        int x = location.blockX() / 8;
                        int y = location.getBlockY();
                        int z = location.getBlockZ() / 8;
                        portalEvent.setCanCreatePortal(true);
                        portalEvent.setTo(new Location(Bukkit.getWorld(new WorldUtil().nether_world_name()), x, y, z));
                    } else {
                        portalEvent.setCancelled(true);
                    }
                } else {
                    gP.setDEFOLT_WORLD_LOCATION(portalEvent.getFrom().add(-1, 0, 0));
                    Location location = portalEvent.getFrom();
                    int x = location.blockX() / 8;
                    int y = location.getBlockY();
                    int z = location.getBlockZ() / 8;
                    portalEvent.setCanCreatePortal(true);
                    portalEvent.setTo(new Location(Bukkit.getWorld(new WorldUtil().nether_world_name()), x, y, z));
                }
                break;
            case MICE:
                MiceCats.setNetherActivated(true);
                gP.setDEFOLT_WORLD_LOCATION(portalEvent.getFrom().add(-1, 0, 0));
                Location location = portalEvent.getFrom();
                int x = location.blockX() / 8;
                int y = location.getBlockY();
                int z = location.getBlockZ() / 8;
                portalEvent.setCanCreatePortal(true);
                portalEvent.setTo(new Location(Bukkit.getWorld(new WorldUtil().nether_world_name()), x, y, z));
                break;
        }
    }

    @EventHandler
    public void userBed(PlayerInteractEvent interactEvent) {
        if (!MiceCats.Is_Game) {
            return;
        }
        if (!interactEvent.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (interactEvent.getClickedBlock() == null) {
            return;
        }
        if (!interactEvent.getClickedBlock().getType().name().contains("BED")) {
            return;
        }
        Player player = interactEvent.getPlayer();
        if (!player.getWorld().getEnvironment().equals(World.Environment.THE_END) &&
                !player.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
            return;
        }
        if (MiceCats.getGameSetting().BED_BANG) {
            return;
        }
        interactEvent.setCancelled(true);
    }
}
