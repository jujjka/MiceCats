package micerat.micecats.cmds;

import micerat.micecats.MiceCats;
import micerat.micecats.utilis.ColorUtil;
import micerat.micecats.utilis.LangUTIL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class PluginMessage {
    public static void GAME_SETTINGS_NULL() {
        Bukkit.getLogger().info(" ========== ERROR | ERROR ==========");
        Bukkit.getLogger().info(" ==== GameSettings doesn't exists ==");
    }

    public static void MESSAGE(Player p, String s, String... args) {
        p.sendMessage(new LangUTIL().getString(MiceCats.getPlayers_lang().get(p), s).formatted(args));
    }

    public static String getTitle1(Player p, String title) {
        return new ColorUtil().format(new LangUTIL().getString(MiceCats.getPlayers_lang().get(p), title));
    }

    public static String getTitle(Player p, String title, String... args) {
        return new ColorUtil().format(new LangUTIL().getString(MiceCats.getPlayers_lang().get(p), title).formatted(args));
    }

    public static String getTitle2(Player p, String title, String args) {
        return new ColorUtil().format(new LangUTIL().getString(MiceCats.getPlayers_lang().get(p), title).formatted(args));
    }

    public static String getTitle3(Player p, String title, List args) {
        return new ColorUtil().format(new LangUTIL().getString(MiceCats.getPlayers_lang().get(p), title).formatted(args));
    }
}
