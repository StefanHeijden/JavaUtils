package config;

import java.util.Map;
import java.util.NoSuchElementException;

public class Configuration {
    Map<String, String> configurationMap;
    String[][] commandsConfigurationArray;
    private static final String ERROR_MESSAGE = "Did not find configuration key: ";

    public Configuration(String configFileLocation, String commandsConfigFileLocation) {
        configurationMap = Configurator.getConfigurationFromFile(configFileLocation);
        commandsConfigurationArray = Configurator.getArrayConfigurationFromFile(commandsConfigFileLocation);
    }

    public String getValue(String key) {
        if(configurationMap.containsKey(key)) {
            return configurationMap.get(key);
        }else {
            throw new NoSuchElementException(ERROR_MESSAGE + key);
        }
    }

    public int getIntValue(String key) {
        if(configurationMap.containsKey(key)) {
            return Integer.parseInt(configurationMap.get(key));
        }else {
            throw new NoSuchElementException(ERROR_MESSAGE + key);
        }
    }

    public boolean getBoolValue(String key) {
        if(configurationMap.containsKey(key)) {
            return Boolean.parseBoolean(configurationMap.get(key));
        }else {
            throw new NoSuchElementException(ERROR_MESSAGE + key);
        }
    }

    public String[] getArrayValue(String key) {
        if(configurationMap.containsKey(key)) {
            return new String[0];
        }else {
            throw new NoSuchElementException(ERROR_MESSAGE + key);
        }
    }
}
