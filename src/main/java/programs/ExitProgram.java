package programs;

import java.util.Map;

public class ExitProgram implements Program {
    @Override
    public boolean run(Map<String, Object> input) {
        return false;
    }
}
