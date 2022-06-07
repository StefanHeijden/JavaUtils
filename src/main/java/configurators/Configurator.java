package configurators;

import configurators.configs.ArrayConfig;
import configurators.configs.Config;
import utilities.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Configurator {
    public static final String APPLICATION_PATH = "C:\\Users\\stheijde\\Repositories\\Java\\JavaUtils\\";
    public static final String CONFIGURATION_SEPARATOR = "=";
    public static final String STRING_LINE_SEPARATOR = "/";

    private Configurator(){}

    public static Map<String, String> getConfigurationFromFile(String filePath) {
        try (Stream<String> configStream = Files.lines(Paths.get(APPLICATION_PATH + filePath))){
            return configStream.map(Config::new).collect(Collectors.toMap(Config::getConfigKey, Config::getConfigValue));
        } catch (IOException e) {
            Logger.log(e);
        }
        return new HashMap<>();
    }

    public static String[] getArrayConfigurationFromFile(String filePath) {
        try (Stream<String> configStream = Files.lines(Paths.get(APPLICATION_PATH + filePath))){
            final ArrayConfig arrayConfig = new ArrayConfig();
            configStream.forEach(arrayConfig::addConfig);
            return arrayConfig.toArray();
        } catch (IOException e) {
            Logger.log(e);
        }
        return new String[0];
    }

}
