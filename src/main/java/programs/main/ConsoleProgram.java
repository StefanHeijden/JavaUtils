package programs.main;

import programs.ExitProgram;
import programs.PrintHelp;
import programs.Program;
import utilities.UserInterface;

import java.util.HashMap;
import java.util.Map;

public class ConsoleProgram implements Program {
    UserInterface ui;
    Map<String, Program> programs;
    Map<String, Object> configs;

    @Override
    public void init(Map<String, Object> input) {
        ui = (UserInterface) input.get("UI");
        programs = new HashMap<>();
        programs.put("exit", new ExitProgram());
        programs.put("help", new PrintHelp());
        configs = new HashMap<>();
        configs.put("PROGRAMS", programs);
    }

}
