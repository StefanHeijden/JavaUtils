package config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Configurator extends AbstractConfigurator{

    @Override
    public Map<String, String> getConfigurationFromFile(String filePath) {
        try (Stream<String> configStream = Files.lines(Paths.get(CONFIG_PATH + filePath))){
            return configStream.map(e -> new Config(e)).collect(Collectors.toMap(Config::getConfigKey, Config::getConfigValue));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    @Override
    public String[] getArrayConfigurationFromFile(String filePath) {
        return new String[0];
    }


    class Config{
        String configKey;
        String configValue;
        Config(String config) {
            configKey = config.split("=")[0].trim();
            configValue = config.split("=")[1].trim();
        }

        String getConfigKey() {
            return configKey;
        }

        String getConfigValue() {
            return configValue;
        }
    }
}
