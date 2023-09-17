package micerat.micecats.cmds;

import micerat.micecats.MiceCats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PauseCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (sender instanceof Player player) {
            if (!MiceCats.Is_Game) {
                PluginMessage.MESSAGE(player, "e.game-not_started");
                return false;
            }
            if (MiceCats.isPaused()) {
                PluginMessage.MESSAGE(player, "e.game_already_paused");
                return false;
            }
            if (player.isOp()) {
                MiceCats.getGameManager().requestGamePause(player);
            }
        }
        return false;
    }
}
