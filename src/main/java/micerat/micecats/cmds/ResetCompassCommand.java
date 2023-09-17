package micerat.micecats.cmds;

import micerat.micecats.GamePlayer;
import micerat.micecats.listeners.user.PlayerHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ResetCompassCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(sender instanceof Player player){
            if(GamePlayer.getGame_player(player) == null){return false;}
            GamePlayer gP = GamePlayer.getGame_player(player);
            if(gP.getTarget() == null){return false;}
            player.getInventory().remove(Material.COMPASS);
            gP.setTarget(null);
            new PlayerHelper().giveCompass(player);
        }
        return false;
    }
}
