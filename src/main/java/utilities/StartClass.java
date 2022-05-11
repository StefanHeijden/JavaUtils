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

    public static void main(String[] args) {
        Map<String, Program> programs = new HashMap<>();
        programs.put("exit", new ExitProgram());
        programs.put("help", new PrintHelp());
        programs.put("clean", new DeleteFilesInFolder());
        programs.put("yaml", new ReadYaml());
        programs.put("elastic", new ProcessElasticExport());
        programs.put("find", new FindFiles());
        programs.put("cd", new ChangeCurrentPath());
        Map<String, Object> configs = new HashMap<>();
        configs.put("FILE_LOCATION", FILE_LOCATION);
        configs.put("TARGET_LOCATION", TARGET_LOCATION);
        configs.put("USER_DIR", USER_DIR);
        configs.put("PROGRAMS", programs);
        new MainConsole("Main", programs, configs);
    }
}
