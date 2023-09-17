package micerat.micecats;

import lombok.Getter;
import lombok.Setter;
import micerat.micecats.cmds.PluginMessage;
import micerat.micecats.core.modulse.GameSettings;
import micerat.micecats.listeners.user.PlayerHelper;
import micerat.micecats.utilis.LangUTIL;
import micerat.micecats.utilis.WorldUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameManager {
    @Getter
    private static GameSettings.Team WinTeam;
    @Getter
    private static List<GamePlayer> All_players = new ArrayList<>();
    @Getter
    private static List<GamePlayer> Death_players = new ArrayList<>();
    @Getter
    @Setter
    private int scheduler;

    public static String intToTime(int time) {
        int hours = time / 3600;
        int minutes = time % 3600 / 60;
        int seconds = time % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public void join_player(GamePlayer gamePlayer, GamePlayer.Role role) {
        if (gamePlayer.getPlayer().isOnline()) {
            if (!All_players.contains(gamePlayer)) {
                return;
            }
            if (Death_players.contains(gamePlayer)) {
                gamePlayer.setRole(role);
                Death_players.remove(gamePlayer);
            }
            if (role == GamePlayer.Role.SPECTATOR) {
                gamePlayer.getPlayer().setGameMode(GameMode.SPECTATOR);
            } else {
                gamePlayer.getPlayer().setGameMode(GameMode.SURVIVAL);
            }

            Player player = gamePlayer.getPlayer();

            if (MiceCats.Is_Game) {
                player.teleport(Bukkit.getWorld(new WorldUtil().world_name()).getSpawnLocation());
            }
            player.getInventory().clear();
            player.closeInventory();
            player.setHealth(20);
            if (MiceCats.second_game_time < 4) {
                addEffects(player);
            }
            if (gamePlayer.getRole() == GamePlayer.Role.CAT || role == GamePlayer.Role.CAT) {
                new PlayerHelper().giveCompass(gamePlayer.getPlayer());
            }
            PluginMessage.MESSAGE(player, "e.player_chat");
        }
    }

    public void addEffects(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 80, 9, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 9, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 80, -9, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 9, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 80, 9, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 80, 9, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 80, 9, false, false));
    }

    public void deletePlayer(GamePlayer gamePlayer, boolean role_command, boolean death) {
        GamePlayer.getGame_players().remove(gamePlayer);
        All_players.remove(gamePlayer);
        MiceCats.getTeamMice().remove(gamePlayer);
        MiceCats.getTeamCat().remove(gamePlayer);
        MiceCats.getTeamSpectator().remove(gamePlayer);
        if (death) {
            Death_players.add(gamePlayer);
        }
        if (!role_command && !death) {
            messageForTeams("e.player_exit", gamePlayer.getPlayer().getName());
        }
    }

    public void requestGamePause(Player sended_pause) {
        Bukkit.getWorld("world").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        Bukkit.getWorld("world").setGameRule(GameRule.DO_WEATHER_CYCLE, false);

        MiceCats.setPaused(true);
        Iterator<GamePlayer> var3 = GamePlayer.getGame_players().iterator();

        while (var3.hasNext()) {
            GamePlayer gamePlayer = var3.next();
            Player player = gamePlayer.getPlayer();
            if (gamePlayer.getRole() != GamePlayer.Role.SPECTATOR) {
                player.setGameMode(GameMode.ADVENTURE);
            }

            player.setInvulnerable(true);
            gamePlayer.setBurningTicks(player.getFireTicks());
            gamePlayer.setFreezeTicks(player.getFreezeTicks());
            gamePlayer.setShieldCooldown(player.getCooldown(Material.SHIELD));
            gamePlayer.setEnderPearlCooldown(player.getCooldown(Material.ENDER_PEARL));
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 10, false, false));
            player.playSound(player, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.0F);
            PluginMessage.MESSAGE(player, "e.game_pause", sended_pause.getName(), "5:00");
        }

        scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(MiceCats.getPlugin(), new Runnable() {
            public void run() {
                Iterator<GamePlayer> var1 = GamePlayer.getGame_players().iterator();

                while (var1.hasNext()) {
                    GamePlayer manhuntPlayer = var1.next();
                    if (manhuntPlayer.getBurningTicks() > 0) {
                        manhuntPlayer.getPlayer().setFireTicks(manhuntPlayer.getBurningTicks());
                    }

                    if (manhuntPlayer.getFreezeTicks() > 0) {
                        manhuntPlayer.getPlayer().setFreezeTicks(manhuntPlayer.getFreezeTicks());
                    }

                    if (manhuntPlayer.getShieldCooldown() > 0) {
                        manhuntPlayer.getPlayer().setCooldown(Material.SHIELD, manhuntPlayer.getShieldCooldown());
                    }

                    if (manhuntPlayer.getEnderPearlCooldown() > 0) {
                        manhuntPlayer.getPlayer().setCooldown(Material.ENDER_PEARL, manhuntPlayer.getEnderPearlCooldown());
                    }
                }

            }
        }, 0L, 1L);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (MiceCats.isPaused()) {
                    removeGamePause();
                }
            }
        }.runTaskLater(MiceCats.getPlugin(), 6000);
    }

    public void removeGamePause() {
        Bukkit.getWorld("world").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        Bukkit.getWorld("world").setGameRule(GameRule.DO_WEATHER_CYCLE, true);
        MiceCats.setPaused(false);
        Bukkit.getScheduler().cancelTask(getScheduler());
        Iterator<GamePlayer> var1 = GamePlayer.getGame_players().iterator();

        while (var1.hasNext()) {
            GamePlayer game_player = var1.next();
            Player player = game_player.getPlayer();
            if (game_player.getRole() != GamePlayer.Role.SPECTATOR) {
                player.setGameMode(GameMode.SURVIVAL);
            }

            player.setInvulnerable(false);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
            player.setFireTicks(game_player.getBurningTicks());
            player.setFreezeTicks(game_player.getFreezeTicks());
            player.setCooldown(Material.SHIELD, game_player.getShieldCooldown());
            player.setCooldown(Material.ENDER_PEARL, game_player.getEnderPearlCooldown());
            game_player.setBurningTicks(0);
            game_player.setFreezeTicks(0);
            game_player.setShieldCooldown(0);
            game_player.setEnderPearlCooldown(0);
            PluginMessage.MESSAGE(player, "e.game_unpase");
        }
    }

    public void timer() {
        MiceCats.setTimer(true);
        new BukkitRunnable() {
            int second = 1;

            @Override
            public void run() {
                if (MiceCats.isTimer()) {
                    if (second < 60) {
                        second++;
                        if (second == 15) {
                            messageForTeams("e.timerMsg", String.valueOf(45));
                        }
                        if (second == 30) {
                            messageForTeams("e.timerMsg", String.valueOf(30));
                        }
                        if (second == 45) {
                            messageForTeams("e.timerMsg", String.valueOf(15));
                        }
                        if (second > 54) {
                            messageForTeams("e.timerFinalMsg", String.valueOf(60 - second));
                        }
                    } else {
                        MiceCats.getPlugin().startGAME();
                        this.cancel();
                    }
                } else {
                    messageForTeams("e.timerCancel");
                    this.cancel();
                }
            }
        }.runTaskTimer(MiceCats.getPlugin(), 20, 20);
    }

    public void restartGame(Player restarter, long seed) {
        MiceCats.setIs_Game(false);
        MiceCats.Is_Game = false;
        MiceCats.setNetherActivated(false);
        MiceCats.getPlugin().getServer().setMotd(MiceCats.getPluginConfig().getString("serverName.motD"));
        Iterator var_player = getAll_players().listIterator();
        while (var_player.hasNext()) {
            GamePlayer gamePlayer = (GamePlayer) var_player.next();
            Player player = gamePlayer.getPlayer();
            if (seed != 0) {
                PluginMessage.MESSAGE(player, "e.game_stop_with-seed", restarter.getName(), String.valueOf(seed));
            } else {
                PluginMessage.MESSAGE(player, "e.game_stop", restarter.getName());
            }
            player.setGameMode(GameMode.ADVENTURE);
            player.teleport(Bukkit.getWorld(new WorldUtil().default_world_name()).getSpawnLocation());
            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1.0F, 1.0F);
            player.getInventory().clear();
            new PlayerHelper().setupItems(player);
        }
        All_players.clear();
        Death_players.clear();
        MiceCats.getTeamSpectator().clear();
        MiceCats.getTeamCat().clear();
        MiceCats.getTeamMice().clear();
        GamePlayer.getGame_players().clear();

        MiceCats.setIs_ChunksReload(true);
        new BukkitRunnable() {
            @Override
            public void run() {
                new WorldUtil().delete();
                new WorldUtil().new_setup(seed);
            }
        }.runTaskLater(MiceCats.getPlugin(), 60);

        for (Player bkPlayer : Bukkit.getOnlinePlayers()) {
            PluginMessage.MESSAGE(bkPlayer, "e.world_reload");
            if (bkPlayer.getGameMode() == GameMode.SPECTATOR) {
                bkPlayer.setGameMode(GameMode.ADVENTURE);
                bkPlayer.teleport(Bukkit.getWorld(new WorldUtil().default_world_name()).getSpawnLocation());
            }
        }
    }

    public void RunnerWin() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (PluginMessage.getTitle1(p, "e.game_runner_win.chat_message") != "") {
                List<String> names = new ArrayList<>();
                for (GamePlayer gamePlayer : MiceCats.getTeamMice()) {
                    names.add(gamePlayer.getPlayer().getName());
                }
                p.sendMessage(PluginMessage.getTitle3(p, "e.game_runner_win.chat_message", names));
            }
        }
    }

    public void HunterWin() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (PluginMessage.getTitle1(p, "e.game_hunter_win.chat_message") != "") {
                List<String> names = new ArrayList<>();
                for (GamePlayer gamePlayer : MiceCats.getTeamCat()) {
                    names.add(gamePlayer.getPlayer().getName());
                }
                p.sendMessage(PluginMessage.getTitle3(p, "e.game_hunter_win.chat_message", names));
            }
        }
    }

    public List<GamePlayer> getTeamForPlayer(GamePlayer gamePlayer) {
        if (MiceCats.getTeamCat().contains(gamePlayer)) {
            return MiceCats.getTeamCat();
        }
        if (MiceCats.getTeamMice().contains(gamePlayer)) {
            return MiceCats.getTeamMice();
        }
        if (MiceCats.getTeamSpectator().contains(gamePlayer)) {
            return MiceCats.getTeamSpectator();
        }
        return null;
    }

    public void changeRole(Player player, GamePlayer.Role role) {
        String now_role = "";
        if (GamePlayer.getGame_player(player) == null) {
            new GamePlayer(player, role);
        } else {
            GamePlayer gp = GamePlayer.getGame_player(player);
            MiceCats.getTeamMice().remove(gp);
            MiceCats.getTeamCat().remove(gp);
            MiceCats.getTeamSpectator().remove(gp);

            assert gp != null;
            gp.setRole(role);
            switch (role) {
                case CAT -> {
                    MiceCats.getTeamCat().add(gp);
                    PluginMessage.MESSAGE(player, "e.game_role.hunter");
                    GamePlayer.setTeam(GamePlayer.Team.RUNNER);
                    now_role = "Раннер";
                }
                case MICE -> {
                    MiceCats.getTeamMice().add(gp);
                    PluginMessage.MESSAGE(player, "e.game_role.runner");
                    GamePlayer.setTeam(GamePlayer.Team.HUNTER);
                    now_role = "Хантер";
                }
                case SPECTATOR -> {
                    MiceCats.getTeamSpectator().add(gp);
                    PluginMessage.MESSAGE(player, "e.game_role.spectator");
                    GamePlayer.setTeam(GamePlayer.Team.SPECTATOR);
                    now_role = "Спектатор";
                }
            }

            Iterator<GamePlayer> gpI = GamePlayer.getGame_players().listIterator();
            while (gpI.hasNext()) {
                GamePlayer gp1 = gpI.next();
                Player p = gp1.getPlayer();
                if (!gp1.equals(gp)) {
                    PluginMessage.MESSAGE(p, "e.player_change_role", p.getName(), now_role);
                } else {
                    PluginMessage.MESSAGE(p, "e.player_change_role_pers");
                }
            }
        }
    }

    public void saturation() {
        for (GamePlayer gP : All_players) {
            gP.getPlayer().addPotionEffect(new PotionEffect(
                    PotionEffectType.SATURATION,
                    PotionEffect.INFINITE_DURATION,
                    0
            ));
            PluginMessage.MESSAGE(gP.getPlayer(), "e.player_saturaion");
        }
    }

    public void messageForTeams(String msg) {
        for (GamePlayer gP : All_players) {
            gP.sendMessage(new LangUTIL().getString(MiceCats.getPlayers_lang().get(gP.getPlayer()), msg));
        }
    }

    public void messageForTeams(String msg, String name) {
        for (GamePlayer gP : All_players) {
            gP.sendMessage(new LangUTIL().getString(MiceCats.getPlayers_lang().get(gP.getPlayer()), msg).formatted(name));
        }
    }

    public void loadChunk(Chunk chunk) {
        Bukkit.getScheduler().runTaskLater(MiceCats.getPlugin(), new Runnable() {
            @Override
            public void run() {
                try {
                    chunk.getWorld().loadChunk(chunk.getX(), chunk.getZ());
                    chunk.getWorld().setChunkForceLoaded(chunk.getX(), chunk.getZ(), true);
                } catch (NoSuchMethodError ignored) {
                }
            }
        }, 1);
    }

    public void loadChunks() {
        MiceCats.setIs_ChunksReload(true);
        World world = Bukkit.getWorld(new WorldUtil().world_name());
        List<Block> chunks_1 = new ArrayList<>();
        List<Block> chunks_2 = new ArrayList<>();
        List<Block> chunks_3 = new ArrayList<>();
        List<Block> chunks_4 = new ArrayList<>();
        assert world != null;
        int xS = world.getSpawnLocation().getBlockX();
        int zS = world.getSpawnLocation().getBlockZ();
        for (int x = xS - 30; x <= xS + 30; x += 16) {
            for (int z = zS - 30; z <= zS + 30; z += 16) {
                chunks_1.add(new Location(world, x, 100, z).getBlock());
            }
        }
        for (int x = xS - 30; x <= xS + 30; x += 16) {
            for (int z = xS - 30; z <= xS + 30; z += 16) {
                chunks_2.add(new Location(world, x, 100, z).getBlock());
            }
        }
        for (int x = xS + 30; x <= xS + 90; x += 16) {
            for (int z = xS - 30; z <= xS + 30; z += 16) {
                chunks_3.add(new Location(world, x, 100, z).getBlock());
            }
        }
        for (int x = xS + 30; x <= xS + 90; x += 16) {
            for (int z = zS + 30; z <= zS + 90; z += 16) {
                chunks_4.add(new Location(world, x, 100, z).getBlock());
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Block block : chunks_1) {
                    block.getChunk().load();
                }
                sendAllWorldMessage("e.worldchunksload", "%25");
                Bukkit.getLogger().info(" ==== chunks load (%25) ==== ");

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Block block : chunks_2) {
                            block.getChunk().load();
                        }
                        sendAllWorldMessage("e.worldchunksload", "%50");
                        Bukkit.getLogger().info(" ==== chunks load (%50) ==== ");

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                for (Block block : chunks_3) {
                                    block.getChunk().load();
                                }
                                sendAllWorldMessage("e.worldchunksload", "%75");
                                Bukkit.getLogger().info(" ==== chunks load (%75) ==== ");

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        for (Block block : chunks_4) {
                                            block.getChunk().load();
                                        }
                                        sendAllWorldMessage("e.worldchunksload", "%100");
                                        Bukkit.getLogger().info(" ==== chunks load (%100) ==== ");
                                        MiceCats.setIs_ChunksReload(false);
                                    }
                                }.runTaskLater(MiceCats.getPlugin(), 200);
                            }
                        }.runTaskLater(MiceCats.getPlugin(), 200);
                    }
                }.runTaskLater(MiceCats.getPlugin(), 200);
            }
        }.runTaskLater(MiceCats.getPlugin(), 100);
    }

    public void sendAllWorldMessage(String msg, String arg) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getWorld().getName().equals(new WorldUtil().default_world_name()))
                p.sendMessage(PluginMessage.getTitle(p, msg, ChatColor.GREEN + arg));
        }
    }

    public void setLangForAllPlayers() {
        Iterator<? extends Player> pI = Bukkit.getOnlinePlayers().iterator();
        String dL = MiceCats.getPlugin().getDefault_lang();

        while (pI.hasNext()) {
            Player player = pI.next();
            String lang_set = MiceCats.getPluginConfig().getString("users." + player.getName());
            if (lang_set != null) {
                MiceCats.getPlugin().setLang(player, lang_set);
            } else {
                MiceCats.getPlugin().setLang(player, dL);
            }
        }
    }

    public void deathPlayer(GamePlayer gamePlayer) {
        Iterator<GamePlayer> pI = GamePlayer.getGame_players().iterator();
        while (pI.hasNext()) {
            if (MiceCats.getTeamCat().size() > 0 || MiceCats.getTeamMice().size() > 0) {
                GamePlayer player = pI.next();
                PluginMessage.MESSAGE(player.getPlayer(), "e.playerDeath", Team(gamePlayer), gamePlayer.getPlayer().getName());
            }
        }
    }

    public String Team(GamePlayer gamePlayer) {
        return switch (MiceCats.getPlayers_lang().get(gamePlayer.getPlayer())) {
            case "ru.yml" -> switch (GamePlayer.getTeam()) {
                case SPECTATOR -> "Спектаторы";
                case HUNTER -> "Хантеры";
                case RUNNER -> "Раннеры";
            };
            case "en.yml" -> switch (GamePlayer.getTeam()) {
                case SPECTATOR -> "Spectators";
                case HUNTER -> "Hunters";
                case RUNNER -> "Runners";
            };
            default -> null;
        };
    }
}
