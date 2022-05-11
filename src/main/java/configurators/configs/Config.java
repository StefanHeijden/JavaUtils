package configurators.configs;

import configurators.Configurator;

public class Config {
    private final String configKey;
    private final String configValue;

    public Config(String config) {
        configKey = config.split(Configurator.CONFIGURATION_SEPARATOR)[0].trim();
        configValue = config.split(Configurator.CONFIGURATION_SEPARATOR)[1].trim();
    }

    public String getConfigKey() {
        return configKey;
    }

    public String getConfigValue() {
        return configValue;
    }
}
