package micerat.micecats;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import micerat.micecats.cmds.PluginMessage;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;

@Data
public class GamePlayer {
    @Getter
    private static ArrayList<GamePlayer> Game_players = new ArrayList<>();
    @Setter
    @Getter
    private static Team Team;
    private Player Player;
    @Setter
    @Getter
    private Role Role;
    private Location DEFOLT_WORLD_LOCATION;
    private Location END_WORLD_LOCATION;
    private Location NETHER_WORLD_LOCATION;

    private boolean IS_GAME;

    private int pauseCooldown;
    private int burningTicks;
    private int freezeTicks;
    private int shieldCooldown;
    private int enderPearlCooldown;

    @Getter
    @Setter
    private Player target;
    @Getter
    @Setter
    private boolean isReady;

    public GamePlayer(Player player, Role role) {
        init(player, role);
        Game_players.add(this);
        GameManager.getAll_players().add(this);
        for (GamePlayer gamePlayer : Game_players) {
            if (!gamePlayer.equals(this)) {
                PluginMessage.MESSAGE(gamePlayer.getPlayer(), "e.player_join", player.getName());
            }
        }
    }

    public static GamePlayer getGame_player(Player player) {
        Iterator<GamePlayer> playersIt;
        playersIt = Game_players.iterator();
        while (playersIt.hasNext()) {
            GamePlayer Gp = playersIt.next();
            if (Gp.getPlayer().equals(player)) {
                return Gp;
            }
        }
        return null;
    }

    public static GamePlayer getDeath_player(Player player) {
        for (GamePlayer Gp : GameManager.getDeath_players()) {
            if (Gp.getPlayer().equals(player)) {
                return Gp;
            }
        }
        return null;
    }

    private void init(Player player, Role role) {
        this.Player = player;
        setRole(role);
        setIS_GAME(MiceCats.isIs_Game());
        switch (role) {
            case CAT -> {
                MiceCats.getTeamCat().add(this);
                PluginMessage.MESSAGE(player, "e.game_role.hunter");
                setTeam(GamePlayer.Team.RUNNER);
            }
            case MICE -> {
                MiceCats.getTeamMice().add(this);
                PluginMessage.MESSAGE(player, "e.game_role.runner");
                setTeam(GamePlayer.Team.HUNTER);
            }
            case SPECTATOR -> {
                MiceCats.getTeamSpectator().add(this);
                PluginMessage.MESSAGE(player, "e.game_role.spectator");
                setTeam(GamePlayer.Team.SPECTATOR);
            }
            default -> throw new IllegalStateException("Unexpected value: " + role);
        }
    }

    public void sendMessage(String message) {
        getPlayer().sendMessage(message);
    }

    public enum Role {
        MICE(),
        CAT,
        SPECTATOR;

        Role() {
        }
    }

    public enum Team {
        RUNNER,
        HUNTER,
        SPECTATOR;

        Team() {

        }
    }
}
