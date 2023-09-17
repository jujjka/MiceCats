package micerat.micecats.utilis;

import micerat.micecats.GamePlayer;
import micerat.micecats.MiceCats;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;

public class WorldUtil {

    public void delete() {
        try {
            FileUtils.deleteDirectory(new File(world_name()));
            FileUtils.deleteDirectory(new File(nether_world_name()));
            FileUtils.deleteDirectory(new File(end_world_name()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Bukkit.getLogger().info("old-worlds delete");
    }

    public void new_setup(long seed) {
        MiceCats.setIs_ChunksReload(true);
        World endWorld = Bukkit.getWorld(end_world_name());
        for (GamePlayer gP : GamePlayer.getGame_players()) {
            Player player = gP.getPlayer();
            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1.0F, 1.0F);
            if (endWorld != null) {
                endWorld.getEnderDragonBattle().getBossBar().removePlayer(gP.getPlayer());
            }
        }
        World world = Bukkit.getWorld(world_name());
        if (world != null) {
            world.setKeepSpawnInMemory(false);
            if (Bukkit.unloadWorld(world, true)) {
                try {
                    FileUtils.deleteDirectory(new File(world_name()));
                } catch (IOException var9) {
                    var9.printStackTrace();
                }
            } else {
                System.out.println("Warning: the world didn't unload");
            }
        }
        World worldNether = Bukkit.getWorld(nether_world_name());
        if (worldNether != null) {
            worldNether.setKeepSpawnInMemory(false);
            if (Bukkit.unloadWorld(worldNether, true)) {
                try {
                    FileUtils.deleteDirectory(new File(nether_world_name()));
                } catch (IOException var8) {
                    var8.printStackTrace();
                }
            } else {
                System.out.println("Warning: the world didn't unload");
            }
        }
        World worldEnd = Bukkit.getWorld(end_world_name());
        if (worldEnd != null) {
            worldEnd.setKeepSpawnInMemory(true);
            if (Bukkit.unloadWorld(worldEnd, false)) {
                try {
                    FileUtils.deleteDirectory(new File(end_world_name()));
                } catch (IOException var7) {
                    var7.printStackTrace();
                }
            } else {
                System.out.println("Warning: the world didn't unload");
            }
        }
        WorldCreator overWorldCreator = (new WorldCreator(world_name())).environment(World.Environment.NORMAL);
        if (seed != 0) {
            overWorldCreator.seed(seed);
        }
        World overWorld = Bukkit.getServer().createWorld(overWorldCreator);
        assert overWorld != null;
        overWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        overWorld.setGameRule(GameRule.DO_INSOMNIA, false);
        overWorld.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        overWorld.setDifficulty(MiceCats.getGameSetting().getDIFFICULTY());

        World nether = Bukkit.getServer().createWorld((new WorldCreator(nether_world_name())).seed(overWorld.getSeed()).environment(World.Environment.NETHER));
        World end = Bukkit.getServer().createWorld((new WorldCreator(end_world_name())).seed(overWorld.getSeed()).environment(World.Environment.THE_END));
        new BukkitRunnable() {
            @Override
            public void run() {
                MiceCats.getGameManager().loadChunks();
            }
        }.runTaskLater(MiceCats.getPlugin(), 100);
    }

    public String default_world_name() {
        return MiceCats.getPluginConfig().getString("game_setting.default-world");
    }

    public String world_name() {
        return MiceCats.getPluginConfig().getString("game_setting.world");
    }

    public String end_world_name() {
        return MiceCats.getPluginConfig().getString("game_setting.world_end");
    }

    public String nether_world_name() {
        return MiceCats.getPluginConfig().getString("game_setting.world_nether");
    }
}
