package config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Configurator extends AbstractConfigurator{

    @Override
    public Map<String, String> getConfigurationFromFile(String filePath) {
        try (Stream<String> configStream = Files.lines(Paths.get(APPLICATION_PATH + filePath))){
            return configStream.map(Config::new).collect(Collectors.toMap(Config::getConfigKey, Config::getConfigValue));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    @Override
    public String[][] getArrayConfigurationFromFile(String filePath) {
        try (Stream<String> configStream = Files.lines(Paths.get(APPLICATION_PATH + filePath))){
            final ArrayConfig arrayConfig = new ArrayConfig();
            configStream.forEach(config -> arrayConfig.addConfig(config));
            return arrayConfig.toTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0][0];
    }

}
