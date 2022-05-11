package programs;

import java.util.Map;

public interface Program {
    String INPUT_PARAMETER_1 = "INPUT_PARAMETER_1";
    String INPUT_PARAMETER_2 = "INPUT_PARAMETER_2";
    String INPUT_PARAMETER_3 = "INPUT_PARAMETER_3";

    default boolean run(Map<String, Object> input) {
        init(input);
        boolean result = work(input);
        close(input);
        return result;
    }

    default void init(Map<String, Object> input) {
    }

    default boolean work(Map<String, Object> input) {
        return false;
    }

    default void close(Map<String, Object> input) {
        input.remove(INPUT_PARAMETER_1);
        input.remove(INPUT_PARAMETER_2);
        input.remove(INPUT_PARAMETER_3);
    }
}
