package micerat.micecats.cmds;

import micerat.micecats.GameManager;
import micerat.micecats.MiceCats;
import micerat.micecats.utilis.LangUTIL;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TimeGameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!MiceCats.Is_Game) {
            if (sender instanceof Player player) {
                sender.sendMessage(new LangUTIL().getString(MiceCats.getPlayers_lang().get(player), "e.game-not_started"));
            } else {
                sender.sendMessage("Game didn't start yet");
            }
            return false;
        } else {
            Player p = (Player) sender;
            sender.sendMessage(ChatColor.GREEN + GameManager.intToTime(MiceCats.second_game_time));
            return false;
        }
    }
}
