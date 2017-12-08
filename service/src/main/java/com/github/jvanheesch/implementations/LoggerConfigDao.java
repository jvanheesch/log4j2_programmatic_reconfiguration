package com.github.jvanheesch.implementations;

import com.github.jvanheesch.interfaces.ILoggerConfig;
import org.apache.commons.lang3.SerializationUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

final class LoggerConfigDao {
    private static final Map<String, ILoggerConfig> LOGGER_CONFIGS = new HashMap<>();

    private LoggerConfigDao() {
    }

    public static ILoggerConfig read(String loggerConfigName) {
        return SerializationUtils.clone(LOGGER_CONFIGS.get(loggerConfigName));
    }

    public static List<ILoggerConfig> findAll() {
        return LOGGER_CONFIGS.values().stream()
                .map(SerializationUtils::clone)
                .collect(Collectors.toList());
    }

    public static ILoggerConfig save(ILoggerConfig loggerConfig) {
        String name = loggerConfig.getName();
        LOGGER_CONFIGS.put(name, SerializationUtils.clone(loggerConfig));
        return LOGGER_CONFIGS.get(name);
    }

    public static void delete(String loggerConfigName) {
        LOGGER_CONFIGS.remove(loggerConfigName);
    }
}
