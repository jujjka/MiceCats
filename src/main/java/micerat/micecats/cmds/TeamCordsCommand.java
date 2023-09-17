package micerat.micecats.cmds;

import micerat.micecats.GamePlayer;
import micerat.micecats.MiceCats;
import micerat.micecats.utilis.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class TeamCordsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(GamePlayer.getGame_player(player) == null){PluginMessage.MESSAGE(player,"e.player_not_user");return false;}

            GamePlayer gP = GamePlayer.getGame_player(player);
            Iterator<GamePlayer> igP = MiceCats.getGameManager().getTeamForPlayer(gP).iterator();
            while (igP.hasNext()){
                GamePlayer gp = igP.next();
                Player user = gp.getPlayer();
                int x = user.getLocation().getBlockX();
                int y = user.getLocation().getBlockY();
                int z = user.getLocation().getBlockZ();
                String cord = new ColorUtil().format("#45A3B8| " + user.getName()  + ":"
                        + " X:" + x + " Y:" + y + " Z:" + z);
                user.sendMessage(cord);
            }
        }
        return false;
    }
}
