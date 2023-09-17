package micerat.micecats.cmds;

import micerat.micecats.GamePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class NightVisionCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (sender instanceof Player player) {
            if (GamePlayer.getGame_player(player) != null) {

                PotionEffect potionEffect = player.getPotionEffect(PotionEffectType.NIGHT_VISION);
                if (potionEffect == null) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, PotionEffect.INFINITE_DURATION, 0, false, false));
                    PluginMessage.MESSAGE(player, "e.player_gamma_on");
                } else {
                    player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    PluginMessage.MESSAGE(player, "e.player_gamma_of");
                }
            }
        }
        return false;
    }
}
