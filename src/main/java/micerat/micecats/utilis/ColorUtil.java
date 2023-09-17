package micerat.micecats.utilis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtil {

    private final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public String format(String msg) {
        String version = Bukkit.getVersion();
        if (version.contains("1.16") || version.contains("1.17") || version.contains("1.18") || version.contains("1.19") || version.contains("1.20")) {
            Matcher matcher = pattern.matcher(msg);
            while (matcher.find()) {
                String color = msg.substring(matcher.start(), matcher.end());
                msg = msg.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
                matcher = pattern.matcher(msg);

            }
        }
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
