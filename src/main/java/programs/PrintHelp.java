package programs;

import java.util.Map;

public class PrintHelp extends Program{
    @Override
    public boolean run(Map<String, Object> input) {
        Map<String, Program> programs = (Map<String, Program>) input.get("PROGRAMS");
        for (String programName : programs.keySet()) {
            System.out.println(programName);
        }
        return true;
    }

}
