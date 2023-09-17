package micerat.micecats;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import micerat.micecats.cmds.*;
import micerat.micecats.cmds.table.JoinGameTable;
import micerat.micecats.cmds.table.SetRoleTable;
import micerat.micecats.core.modulse.GameSettings;
import micerat.micecats.listeners.gui.*;
import micerat.micecats.listeners.user.Player$Listener;
import micerat.micecats.listeners.user.PlayerHelper;
import micerat.micecats.listeners.user.world.EndPortal$Listener2;
import micerat.micecats.listeners.user.world.NetherPortal$Listener;
import micerat.micecats.utilis.FilterConsole;
import micerat.micecats.utilis.LangUTIL;
import micerat.micecats.utilis.SettingUTIL;
import micerat.micecats.utilis.WorldUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public final class MiceCats extends JavaPlugin {
    public static int second_game_time = 0;
    @Getter
    @Setter
    public static boolean Is_Game;
    @Getter
    @Setter
    public static boolean Is_ChunksReload;
    @Getter
    @Setter
    public static boolean netherActivated;
    @Getter
    private static MiceCats Plugin;
    @Getter
    private static FileConfiguration PluginConfig;
    @Getter
    private static HashMap<Player, String> players_lang = new HashMap<>();
    @Getter
    @Setter
    private static GameSettings GameSetting;
    @Getter
    private static List<GamePlayer> TeamMice = new ArrayList<>();
    @Getter
    private static List<GamePlayer> TeamCat = new ArrayList<>();
    @Getter
    private static List<GamePlayer> TeamSpectator = new ArrayList<>();
    @Getter
    private static GameManager GameManager;
    @Getter
    @Setter
    private static boolean isPaused;
    @Getter
    @Setter
    private static GamePlayer.Role WinTeam;
    @Getter
    private static GameVoting Voting;
    @Getter
    @Setter
    private static int minH;
    @Getter
    @Setter
    private static int minR;
    @Getter
    @Setter
    private static boolean timer;
    @Getter
    @Setter
    private String default_lang;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info(" ========== Manhunt Game ============");
        this.saveDefaultConfig();
        Plugin = this;
        PluginConfig = getConfig();
        if (!MiceCats.getPluginConfig().contains("game_setting")) {
            PluginMessage.GAME_SETTINGS_NULL();
        }
        setMinH(getConfig().getInt("game_setting.min_hunters"));
        setMinR(getConfig().getInt("game_setting.min_runners"));

        LangUTIL.setupFiles();
        this.default_lang = PluginConfig.getString("default_lang");
        GameManager = new GameManager();
        GameManager.setLangForAllPlayers();
        Voting = new GameVoting();
        setGameSetting(new SettingUTIL().get_game_settings());

        WorldUtil WU = new WorldUtil();
        if(Bukkit.getWorld(WU.world_name()) == null){
            new BukkitRunnable() {
                @Override
                public void run() {
                    WU.new_setup(0);
                }
            }.runTaskLater(this, 100);
        } else {
            GameManager.loadChunks();
        }
        startup();
        getCommand("settings").setExecutor(new SettingCommand());
        getCommand("heal").setExecutor(new SaturationCommand());
        getCommand("cords").setExecutor(new CordsCommand());
        getCommand("joinGame").setExecutor(new JoinGameCommand());
        getCommand("joinGame").setTabCompleter(new JoinGameTable());
        getCommand("pauseGame").setExecutor(new PauseCommand());
        getCommand("resetseed").setExecutor(new RestartGameCommand());
        getCommand("start").setExecutor(new StartGameCommand());
        getCommand("timeGame").setExecutor(new TimeGameCommand());
        getCommand("unpauseGame").setExecutor(new UnpauseCommand());
        getCommand("role").setExecutor(new SetRoleCommand());
        getCommand("nightvision").setExecutor(new NightVisionCommand());
        getCommand("role").setTabCompleter(new SetRoleTable());
        getCommand("reloadWorld").setExecutor(new ReloadWorldCommand());
        getCommand("teamCords").setExecutor(new TeamCordsCommand());
        getCommand("teamChat").setExecutor(new TeamChatCommand());
        getCommand("mhCompass").setExecutor(new CompassCommand());
        getCommand("resetCompass").setExecutor(new ResetCompassCommand());

        getServer().getPluginManager().registerEvents(new Player$Listener(), this);
        getServer().getPluginManager().registerEvents(new PersonalLangGUI$Listener(), this);
        getServer().getPluginManager().registerEvents(new PersonalSettingsGUI$Listener(), this);
        getServer().getPluginManager().registerEvents(new RoleGUI$Listener(), this);
        getServer().getPluginManager().registerEvents(new BannedGUI$Listener(), this);
        getServer().getPluginManager().registerEvents(new DifficultyGUI$Listener(), this);
        getServer().getPluginManager().registerEvents(new GameSettingsGUI$Listener(), this);
        getServer().getPluginManager().registerEvents(new EndPortal$Listener2(), this);
        getServer().getPluginManager().registerEvents(new NetherPortal$Listener(), this);
        getServer().getPluginManager().registerEvents(new CompassGUI$Listener(),this);

        getServer().setMotd(getConfig().getString("serverName.motD"));
        ((Logger) LogManager.getRootLogger()).addFilter(new FilterConsole());

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld().getName().equals(new WorldUtil().default_world_name())) {
                player.setGameMode(GameMode.ADVENTURE);
            }
        }
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld().getName().equals(new WorldUtil().world_name())) {
                player.teleport(Bukkit.getWorld(new WorldUtil().default_world_name()).getSpawnLocation());
                player.getInventory().clear();
                new PlayerHelper().setupItems(player);
            }
        }
        this.reloadConfig();
    }

    public void startGAME() {
        setTimer(false);
        Voting.VotingItog();
        second_game_time = 0;
        setIs_Game(true);
        Is_Game = true;
        getServer().setMotd(getConfig().getString("serverName.startMod"));
        Iterator<GamePlayer> gP = micerat.micecats.GameManager.getAll_players().iterator();
        while (gP.hasNext()) {
            GamePlayer gM = gP.next();
            getGameManager().join_player(gM, gM.getRole());
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Is_Game) {
                    second_game_time++;
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(this, 0, 20);
    }

    public void startup() {
        System.out.println("|============================================|");
        System.out.println("|   <        |  MANHUNT-GAMES  |        >    |");
        System.out.println("|     <         | CraftLab |          >      |");
        System.out.println("|============================================|");
    }

    public void setLang(Player player, String lang) {
        if (players_lang.containsKey(player)) {
            PluginConfig.set("users." + player.getName(), lang);
            players_lang.put(player, lang);
        } else {
            players_lang.put(player, lang);
            PluginConfig.set("users." + player.getName(), lang);
        }
    }
}
