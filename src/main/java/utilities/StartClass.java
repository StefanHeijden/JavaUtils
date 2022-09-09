package utilities;

import applications.consoles.MainConsole;
import programs.*;
import programs.main.*;

import java.util.HashMap;
import java.util.Map;

public class StartClass {
    private static final Map<String, Program> PROGRAMS = new HashMap<>();
    private static final Map<String, Object> CONFIGS = new HashMap<>();
    
    public static void init() {
        PROGRAMS.put("exit", new ExitProgram());
        PROGRAMS.put("help", new PrintHelp());
        PROGRAMS.put("clean", new DeleteFilesInFolder());
        PROGRAMS.put("yaml", new ReadYaml());
        PROGRAMS.put("elastic", new ProcessElasticExport());
        PROGRAMS.put("find", new FindFiles());
        PROGRAMS.put("cd", new ChangeCurrentPath());
        PROGRAMS.put("ls", new ListDirectory());
        PROGRAMS.put("json", new ReadJson());
        PROGRAMS.put("awa", new AnalyzeWatsApp());
        CONFIGS.put("PROGRAMS", PROGRAMS);
    }

    public static void start(){
        new MainConsole("Main", PROGRAMS, CONFIGS);
    }

    public static void main(String[] args) {
        try {
            init();
            UserInterface.init();
            start();
        } catch (Exception e) {
            Logger.log(e);
        }
        
    }
}
