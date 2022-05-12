package utilities;

import applications.consoles.MainConsole;
import programs.*;
import programs.main.*;

import java.util.HashMap;
import java.util.Map;

public class StartClass {
    public static final String FILE_LOCATION = "/files/";
    public static final String TARGET_LOCATION = "/results/";
    public static final String USER_DIR = "user.dir";
    private static final Map<String, Program> PROGRAMS = new HashMap<>();
    private static final Map<String, Object> CONFIGS = new HashMap<>();
    
    private static void init() {
        PROGRAMS.put("exit", new ExitProgram());
        PROGRAMS.put("help", new PrintHelp());
        PROGRAMS.put("clean", new DeleteFilesInFolder());
        PROGRAMS.put("yaml", new ReadYaml());
        PROGRAMS.put("elastic", new ProcessElasticExport());
        PROGRAMS.put("find", new FindFiles());
        PROGRAMS.put("cd", new ChangeCurrentPath());
        CONFIGS.put("FILE_LOCATION", FILE_LOCATION);
        CONFIGS.put("TARGET_LOCATION", TARGET_LOCATION);
        CONFIGS.put("USER_DIR", USER_DIR);
        CONFIGS.put("PROGRAMS", PROGRAMS);
    }

    public static void main(String[] args) {
        try {
            init();
            new MainConsole("Main", PROGRAMS, CONFIGS);
        } catch (Exception e) {
            Logger.log(e);
        }
        
    }
}
