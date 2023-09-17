package micerat.micecats.cmds.table;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class SetRoleTable implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp()) {
            if (args.length == 2) {
                List<String> list = new ArrayList<>();
                list.add("Hunter");
                list.add("Runner");
                list.add("Spectator");
                return list;
            }
        }

        return null;
    }
}
