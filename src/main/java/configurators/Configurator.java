package configurators;

import configurators.configs.ArrayConfig;
import configurators.configs.Config;
import configurators.configs.TableConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Configurator {
    public static final String APPLICATION_PATH = "C:\\Users\\stheijde\\Repositories\\Java\\java-hello-world-with-maven-master\\";
    public static final String CONFIGURATION_SEPARATOR = "=";

    private Configurator(){}

    public static Map<String, String> getConfigurationFromFile(String filePath) {
        try (Stream<String> configStream = Files.lines(Paths.get(APPLICATION_PATH + filePath))){
            return configStream.map(Config::new).collect(Collectors.toMap(Config::getConfigKey, Config::getConfigValue));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public static String[] getArrayConfigurationFromFile(String filePath) {
        try (Stream<String> configStream = Files.lines(Paths.get(APPLICATION_PATH + filePath))){
            final ArrayConfig arrayConfig = new ArrayConfig();
            configStream.forEach(arrayConfig::addConfig);
            return arrayConfig.toArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    public static String[][] getTableConfigurationFromFile(String filePath) {
        try (Stream<String> configStream = Files.lines(Paths.get(APPLICATION_PATH + filePath))){
            final TableConfig tableConfig = new TableConfig();
            configStream.forEach(tableConfig::addConfig);
            return tableConfig.toTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0][0];
    }

}
