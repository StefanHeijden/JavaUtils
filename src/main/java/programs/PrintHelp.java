package programs;

import utilities.UserInterface;

import java.util.Map;

public class PrintHelp implements Program{
    @Override
    public boolean run(Map<String, Object> input) {
        @SuppressWarnings("unchecked") Map<String, Program> programs = (Map<String, Program>) input.get("PROGRAMS");
        for (String programName : programs.keySet()) {
            UserInterface.printLine(programName);
        }
        return true;
    }
}
