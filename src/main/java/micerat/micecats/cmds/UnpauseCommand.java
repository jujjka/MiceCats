package micerat.micecats.cmds;

import micerat.micecats.GamePlayer;
import micerat.micecats.MiceCats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UnpauseCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (sender instanceof Player player) {
            GamePlayer game_player = GamePlayer.getGame_player(player);

            if (!MiceCats.Is_Game) {
                PluginMessage.MESSAGE(player, "e.game-not_started");
                return false;
            }
            if (!MiceCats.isPaused()) {
                PluginMessage.MESSAGE(player, "e.game_not_pause");
                return false;
            }
            if (player.isOp()) {
                MiceCats.getGameManager().removeGamePause();
            }
        }
        return false;
    }
}
