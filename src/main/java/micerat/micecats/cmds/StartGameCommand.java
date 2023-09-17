package micerat.micecats.cmds;

import micerat.micecats.GamePlayer;
import micerat.micecats.MiceCats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StartGameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (sender instanceof Player player) {
            if (MiceCats.Is_Game) {
                PluginMessage.MESSAGE(player, "e.game_already-start");
                return false;
            }
            if (MiceCats.isIs_ChunksReload()) {
                PluginMessage.MESSAGE(player, "e.game_notPermCmd");
                return false;
            }
            if (GamePlayer.getGame_players().size() < 1) {
                PluginMessage.MESSAGE(player, "e.game_many_players");
                return false;
            }
            if (player.isOp()) {
                MiceCats.getPlugin().startGAME();
            }
        }
        return false;
    }
}
