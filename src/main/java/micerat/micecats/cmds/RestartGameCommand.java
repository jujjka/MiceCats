package micerat.micecats.cmds;

import micerat.micecats.MiceCats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RestartGameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (!MiceCats.Is_Game) {
                PluginMessage.MESSAGE(player, "e.game-not_started");
                return false;
            }
            if (!player.isOp()) {
                PluginMessage.MESSAGE(player, "e.onlyOp");
                return false;
            }
            if (MiceCats.isIs_ChunksReload()) {
                PluginMessage.MESSAGE(player, "e.game_notPermCmd");
                return false;
            }
            if (args.length >= 1) {
                long seed;
                try {
                    seed = Long.parseLong(args[0]);
                } catch (NumberFormatException e) {
                    PluginMessage.MESSAGE(player, "e.arguments_notType");
                    return false;
                }
                MiceCats.getGameManager().restartGame(player, seed);
            } else {
                MiceCats.getGameManager().restartGame(player, 0);
            }
            MiceCats.setIs_Game(false);
        }
        return false;
    }
}
