package utilities;

import main.MainConsole;
import programs.Program;
import programs.main.DeleteFilesInFolder;
import programs.main.FindFiles;
import programs.main.ReadYaml;

import java.util.HashMap;
import java.util.Map;

public class StartClass {
    public static final String FILE_LOCATION = "/files/";
    public static final String TARGET_LOCATION = "/results/";
    public static final String USER_DIR = "user.dir";

    public static void main(String[] args) {
        // read config?
        // setup ui?
        Map<String, Program> programs = new HashMap<>();
        programs.put("clean", new DeleteFilesInFolder());
        programs.put("yaml", new ReadYaml());
        programs.put("find", new FindFiles());
        Map<String, Object> configs = new HashMap<>();
        configs.put("FILE_LOCATION", FILE_LOCATION);
        configs.put("TARGET_LOCATION", TARGET_LOCATION);
        configs.put("USER_DIR", USER_DIR);
        new MainConsole("Main", programs, configs);
    }
}
