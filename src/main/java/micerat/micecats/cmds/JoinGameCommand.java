package micerat.micecats.cmds;

import micerat.micecats.GameManager;
import micerat.micecats.GamePlayer;
import micerat.micecats.MiceCats;
import micerat.micecats.utilis.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class JoinGameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (!MiceCats.Is_Game) {
                PluginMessage.MESSAGE(player, "e.game-not_started");
                return false;
            }
            if (GamePlayer.getGame_player(player) != null) {
                PluginMessage.MESSAGE(player, "e.game-already_have-you");
                return false;
            }
            if (args.length < 1) {
                PluginMessage.MESSAGE(player, "e.arguments_notEnough");
                return false;
            }
            String arg = args[0];
            GamePlayer.Role role = null;
            if (!arg.equalsIgnoreCase("Hunter") && !arg.equalsIgnoreCase("Spectator")) {
                PluginMessage.MESSAGE(player, "e.arguments_notType");
                return false;
            }
            if (arg.equalsIgnoreCase("Hunter")) {
                role = GamePlayer.Role.CAT;
            } else if (arg.equalsIgnoreCase("Spectator")) {
                role = GamePlayer.Role.SPECTATOR;
            } else if (arg.equalsIgnoreCase("Runner")) {
                PluginMessage.MESSAGE(player, "e.dontPerm");
                return false;
            }
            if (GamePlayer.getDeath_player(player) != null) {
                if (MiceCats.getGameSetting().isDEATH_RUNNERS()) {
                    if (GamePlayer.getDeath_player(player).getRole().equals(GamePlayer.Role.MICE) && role != GamePlayer.Role.SPECTATOR) {
                        PluginMessage.MESSAGE(player, "e.dontPerm");
                        return false;
                    }
                    GamePlayer gP = new GamePlayer(player, role);
                    GameManager.getDeath_players().remove(GamePlayer.getDeath_player(player));
                    MiceCats.getGameManager().join_player(gP, role);
                }
            } else if (GamePlayer.getGame_player(player) != null) {
                if (GamePlayer.getGame_player(player).getRole() == GamePlayer.Role.SPECTATOR) {
                    player.teleport(Bukkit.getWorld(new WorldUtil().world_name()).getSpawnLocation());
                    player.setGameMode(GameMode.SPECTATOR);
                }
            } else {if (role == GamePlayer.Role.CAT) {
                    if (!MiceCats.getGameSetting().LATERS_PLAYER) {
                        PluginMessage.MESSAGE(player, "e.dontPerm");
                        return false;
                    }
                    GamePlayer gP = new GamePlayer(player, role);
                    MiceCats.getGameManager().join_player(gP, role);
                } else {
                    GamePlayer gP = new GamePlayer(player, GamePlayer.Role.SPECTATOR);
                    MiceCats.getGameManager().join_player(gP, GamePlayer.Role.SPECTATOR);
                }
            }
        }
        return false;
    }
}
