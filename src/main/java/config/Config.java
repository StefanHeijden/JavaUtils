package config;

public class Config {
    String configKey;
    String configValue;
    Config(String config) {
        configKey = config.split(Configurator.CONFIGURATION_SEPARATOR)[0].trim();
        configValue = config.split(Configurator.CONFIGURATION_SEPARATOR)[1].trim();
    }

    String getConfigKey() {
        return configKey;
    }

    String getConfigValue() {
        return configValue;
    }
}
