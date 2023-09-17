package micerat.micecats.utilis;

import micerat.micecats.GameManager;
import micerat.micecats.GamePlayer;
import micerat.micecats.MiceCats;
import micerat.micecats.cmds.PluginMessage;
import micerat.micecats.core.modulse.GameSettings;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.configuration.file.FileConfiguration;

public class SettingUTIL {
    FileConfiguration c = MiceCats.getPluginConfig();

    public GameSettings get_game_settings() {
        String settings = "game_setting.";
        if (!c.contains("game_setting")) {
            PluginMessage.GAME_SETTINGS_NULL();
            return null;
        }
        GameSettings gS = new GameSettings();
        gS.setTEAM_DAMAGE(c.getBoolean(settings + "team_damage"));
        gS.setBED_BANG(c.getBoolean(settings + "bed_band"));
        gS.setPOTION(c.getBoolean(settings + "strength_potion"));
        gS.setDEATH_RUNNERS(c.getBoolean(settings + "death_runner_made_hunter"));
        gS.setLATERS_PLAYER(c.getBoolean(settings + "laters_made_hunter"));
        gS.setNETHER_PASS(c.getBoolean(settings + "nether_pass_for_cat"));
        gS.setDIFFICULTY(Difficulty.valueOf(c.getString(settings + "difficulty")));
        gS.setSAVE_INVENTORY(c.getBoolean(settings + "save_inventory"));

        return gS;
    }
}
