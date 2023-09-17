package micerat.micecats;

import lombok.Data;

import micerat.micecats.core.modulse.GameSettings;
import org.bukkit.Difficulty;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Data
public class GameVoting {
    private int team_damageVotes = 0;
    private int bed_bandVotes = 0;
    private int potion_effectVotes = 0;
    private int death_runnerVotes = 0;
    private int laterVotes = 0;
    private int netherPassVotes = 0;
    private int save_inventoryVotes = 0;

    private int peaceful_difficultyVotes = 0;
    private int easy_difficultyVotes = 0;
    private int normal_difficultyVotes = 0;
    private int hard_difficultyVotes = 0;

    private List<Player> playerTeamDamage = new ArrayList<>();
    private List<Player> playerBedBand = new ArrayList<>();
    private List<Player> playerPotion = new ArrayList<>();
    private List<Player> playerDeathRunner = new ArrayList<>();
    private List<Player> playerlaterVotes = new ArrayList<>();
    private List<Player> playerNetherPass = new ArrayList<>();
    private List<Player> playerSaveInventory = new ArrayList<>();
    private List<Player> playerPeaceful = new ArrayList<>();
    private List<Player> playerEasy = new ArrayList<>();
    private List<Player> playerNormal = new ArrayList<>();
    private List<Player> playerHard = new ArrayList<>();

    private HashMap<Player,Integer> player_voting = new HashMap<>();

    public void VotingItog(){
        int minVotes = MiceCats.getPluginConfig().getInt("game_setting.minVotesForClaim");
        GameSettings gS = MiceCats.getGameSetting();
        if(team_damageVotes >= minVotes){
            gS.setTEAM_DAMAGE(!gS.TEAM_DAMAGE);
        }
        if(bed_bandVotes >= minVotes){
            gS.setBED_BANG(!gS.BED_BANG);
        }
        if(potion_effectVotes >= minVotes){
            gS.setPOTION(!gS.POTION);
        }
        if(death_runnerVotes >= minVotes){
            gS.setDEATH_RUNNERS(!gS.DEATH_RUNNERS);
        }
        if(laterVotes >= minVotes){
            gS.setLATERS_PLAYER(!gS.LATERS_PLAYER);
        }
        if(netherPassVotes >= minVotes){
            gS.setNETHER_PASS(!gS.NETHER_PASS);
        }
        if(save_inventoryVotes >= minVotes){
            gS.setSAVE_INVENTORY(!gS.SAVE_INVENTORY);
        }
        //настройки сложности
        if(peaceful_difficultyVotes > easy_difficultyVotes && peaceful_difficultyVotes > normal_difficultyVotes && peaceful_difficultyVotes > hard_difficultyVotes){
            gS.setDIFFICULTY(Difficulty.PEACEFUL);
        }
        if(easy_difficultyVotes > peaceful_difficultyVotes && easy_difficultyVotes > normal_difficultyVotes && easy_difficultyVotes > hard_difficultyVotes){
            gS.setDIFFICULTY(Difficulty.EASY);
        }
        if(normal_difficultyVotes > easy_difficultyVotes && normal_difficultyVotes > peaceful_difficultyVotes && normal_difficultyVotes > hard_difficultyVotes){
            gS.setDIFFICULTY(Difficulty.NORMAL);
        }
        if(hard_difficultyVotes > peaceful_difficultyVotes && hard_difficultyVotes > easy_difficultyVotes && hard_difficultyVotes > normal_difficultyVotes){
            gS.setDIFFICULTY(Difficulty.HARD);
        }

        MiceCats.setGameSetting(gS);
    }
}
