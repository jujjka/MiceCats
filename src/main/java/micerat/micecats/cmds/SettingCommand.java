package micerat.micecats.cmds;

import micerat.micecats.MiceCats;
import micerat.micecats.gui.SettingsGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SettingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (sender instanceof Player player) {
            if (!MiceCats.isIs_ChunksReload()) {
                new SettingsGUI(player).getInventory();
            }
        }
        return false;
    }
}
