package programs;

import java.util.Map;

public interface Program {
    String INPUT_PARAMETER_1 = "INPUT_PARAMETER_1";
    String INPUT_PARAMETER_2 = "INPUT_PARAMETER_2";
    String INPUT_PARAMETER_3 = "INPUT_PARAMETER_3";

    default boolean run(Map<String, Object> input) {
        return false;
    }
}
