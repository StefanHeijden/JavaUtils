package config;

import java.util.ArrayList;
import java.util.List;

public class TableConfig {
    List<String> configKeys;
    List<String> configValues;

    TableConfig() {
        configKeys = new ArrayList<>();
        configValues = new ArrayList<>();
    }

    public void addConfig(String config) {
        configKeys.add(config.split(Configurator.CONFIGURATION_SEPARATOR)[0].trim());
        configValues.add(config.split(Configurator.CONFIGURATION_SEPARATOR)[1].trim());
    }

    public String[][] toTable() {
        if (configKeys.size() == configValues.size()) {
            String[][] result = new String[configValues.size()][2];
            for(int i = 0; i < result.length; i++) {
                result[i][0] = configValues.get(i);
                result[i][1] = configKeys.get(i);
            }
            return result;
        } else {
            throw new IllegalArgumentException("While creating configuration: length of config keys and values must be the same");
        }
    }

}
