package micerat.micecats.cmds;

import micerat.micecats.GamePlayer;
import micerat.micecats.MiceCats;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;

public class TeamChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                PluginMessage.MESSAGE(player, "e.player_chat_notText");
                return false;
            }
            if (GamePlayer.getGame_player(player) == null) {
                PluginMessage.MESSAGE(player, "e.player_not_user");
                return false;
            }
            Iterator<String> argsI = Arrays.stream(args).iterator();
            String msg = "";
            while (argsI.hasNext()) {
                String I = argsI.next();
                msg = msg + " " + I;
            }

            GamePlayer gP = GamePlayer.getGame_player(player);
            Iterator<GamePlayer> igP = MiceCats.getGameManager().getTeamForPlayer(gP).iterator();
            while (igP.hasNext()) {
                if (!igP.next().equals(gP)) {
                    GamePlayer gp = igP.next();
                    Player p = gp.getPlayer();
                    String for_gp = "<%s>%s";
                    p.sendMessage(for_gp.formatted(ChatColor.GREEN + p.getName(), msg));
                }
            }
        }
        return false;
    }
}
