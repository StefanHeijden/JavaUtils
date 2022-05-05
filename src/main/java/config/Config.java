package config;

public class Config {
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
