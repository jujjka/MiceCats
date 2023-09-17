package micerat.micecats.cmds;

import micerat.micecats.GamePlayer;
import micerat.micecats.MiceCats;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetRoleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (MiceCats.Is_Game) {
                PluginMessage.MESSAGE(player, "e.game_already-start");
                return false;
            }
            if (args.length < 2) {
                PluginMessage.MESSAGE(player, "e.arguments_notEnough");
                return false;
            }
            if (Bukkit.getPlayer(args[0]) == null) {
                PluginMessage.MESSAGE(player, "e.arg_notPlayer");
                return false;
            }
            if (!args[1].equals("Hunter") && !args[1].equals("Runner") && !args[1].equals("Spectator")) {
                PluginMessage.MESSAGE(player, "e.arg_notRole");
                return false;
            }
            if (MiceCats.Is_Game) {
                PluginMessage.MESSAGE(player, "e.game_notPermCmd");
                return false;
            }
            Player target_role = Bukkit.getPlayer(args[0]);
            GamePlayer.Role role = null;
            switch (args[1]) {
                case "Hunter":
                    role = GamePlayer.Role.valueOf("CAT");
                    break;
                case "Runner":
                    role = GamePlayer.Role.valueOf("MICE");
                    break;
                case "Spectator":
                    role = GamePlayer.Role.valueOf("SPECTATOR");
                    break;
            }
            if (player.isOp()) {
                if (GamePlayer.getGame_player(target_role) != null) {
                    MiceCats.getGameManager().deletePlayer(GamePlayer.getGame_player(target_role), true, false);
                    new GamePlayer(target_role, role);
                } else {
                    new GamePlayer(target_role, role);
                }
            }
        }
        return false;
    }
}
