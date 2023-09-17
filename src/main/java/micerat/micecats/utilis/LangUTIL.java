package micerat.micecats.utilis;

import micerat.micecats.MiceCats;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LangUTIL {
    private final File folder = MiceCats.getPlugin().getServer().getPluginManager().getPlugin("MiceCats").getDataFolder();
    private FileConfiguration customFile;

    public static void setupFiles() {
        MiceCats mC = MiceCats.getPlugin();
        File lang = new File(mC.getDataFolder(), "lang");
        mC.saveResource("lang/ru.yml", true);
        mC.saveResource("lang/en.yml", true);
        if (!lang.exists()) {
            lang.mkdirs();
        }
    }

    private File getLangDirectory() {
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.getName().equals("lang")) {
                return file;
            }
        }
        return null;
    }

    private File getLang(String lang) {
        File directory = getLangDirectory();
        for (File file_lang : directory.listFiles()) {
            if (file_lang.getName().contains(lang)) {
                return file_lang;
            }
        }
        return null;
    }

    public String getString(String lang, String msg) {
        this.customFile = YamlConfiguration.loadConfiguration(getLang(lang));
        return new ColorUtil().format(customFile.getString(msg));
    }
}
