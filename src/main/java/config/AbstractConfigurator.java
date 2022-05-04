package config;

import java.util.Map;

public abstract class AbstractConfigurator {
    public static final String CONFIG_PATH = "C:\\Users\\stheijde\\Repositories\\Java\\java-hello-world-with-maven-master\\configs\\";
    public abstract Map<String, String> getConfigurationFromFile(String filePath);
    public abstract String[] getArrayConfigurationFromFile(String filePath);
}
