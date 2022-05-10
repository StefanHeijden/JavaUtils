package programs;

import utilities.UserInterface;

import java.util.Map;

public class PrintHelp implements Program{
    @Override
    public boolean run(Map<String, Object> input) {
        @SuppressWarnings("unchecked") Map<String, Program> programs = (Map<String, Program>) input.get("PROGRAMS");
        final UserInterface ui = (UserInterface) input.get("UI");
        for (String programName : programs.keySet()) {
            ui.printLine(programName);
        }
        return true;
    }
}
