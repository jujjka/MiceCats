package micerat.micecats.cmds;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CordsCommand implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String extraText = "";
        int x;
        if (args.length > 0) {
            String[] var6 = args;
            int var7 = args.length;

            for (x = 0; x < var7; ++x) {
                String s = var6[x];
                extraText = extraText.concat(s);
            }
        }
        if (sender instanceof Player player) {
            Location playerLocation = player.getLocation();
            x = playerLocation.getBlockX();
            int y = playerLocation.getBlockY();
            int z = playerLocation.getBlockZ();
            player.chat("X: " + x + " Y: " + y + " Z: " + z + " " + extraText);
        } else {
            sender.sendMessage(ChatColor.RED + "Only players allowed to use this command");
        }

        return false;
    }
}
