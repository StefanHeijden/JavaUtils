package config;

import java.util.ArrayList;
import java.util.List;

public class ArrayConfig {
    List<String> configValues;

    ArrayConfig() {
        configValues = new ArrayList<>();
    }

    public void addConfig(String config) {
        configValues.add(config);
    }

    public String[] toArray() {
        String[] result = new String[configValues.size()];
        for(int i = 0; i < result.length; i++) {
            result[i] = configValues.get(i);
        }
        return result;
    }

}
