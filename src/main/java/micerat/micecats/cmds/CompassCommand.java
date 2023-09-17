package micerat.micecats.cmds;

import micerat.micecats.GamePlayer;
import micerat.micecats.MiceCats;
import micerat.micecats.gui.CompassGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CompassCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (sender instanceof Player player) {
            if (!MiceCats.Is_Game) {
                PluginMessage.MESSAGE(player, "e.game-not_started");
                return false;
            }
            if (GamePlayer.getGame_player(player) == null) {
                PluginMessage.MESSAGE(player, "e.dontPerm");
                return false;
            }
            GamePlayer gP = GamePlayer.getGame_player(player);
            if (gP.getRole() != GamePlayer.Role.CAT) {
                PluginMessage.MESSAGE(player, "e.dontPerm");
                return false;
            }
            new CompassGUI(player);
        }
        return false;
    }
}
