package programs;

import programs.Program;

import java.util.Map;

public class ExitProgram implements Program {
    @Override
    public boolean run(Map<String, Object> input) {
        return false;
    }
}
