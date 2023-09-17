package micerat.micecats.core.modulse;

import lombok.Data;
import org.bukkit.Difficulty;

@Data
public class GameSettings {
    public boolean TEAM_DAMAGE;
    public boolean BED_BANG;
    public boolean POTION;
    public boolean DEATH_RUNNERS;
    public boolean LATERS_PLAYER;
    public boolean NETHER_PASS;
    public Difficulty DIFFICULTY;
    public boolean SAVE_INVENTORY;


    public enum Team {
        MICE(),
        CAT(),
        NOTHING;

        Team() {
        }
    }
}
