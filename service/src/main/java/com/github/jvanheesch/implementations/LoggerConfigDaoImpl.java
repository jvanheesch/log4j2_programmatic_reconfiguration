package com.github.jvanheesch.implementations;

import com.github.jvanheesch.interfaces.ILoggerConfig;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
class LoggerConfigDaoImpl implements LoggerConfigDao {
    private static final Map<String, ILoggerConfig> LOGGER_CONFIGS = new HashMap<>();

    public ILoggerConfig read(String loggerConfigName) {
        return SerializationUtils.clone(LOGGER_CONFIGS.get(loggerConfigName));
    }

    public List<ILoggerConfig> findAll() {
        return LOGGER_CONFIGS.values().stream()
                .map(SerializationUtils::clone)
                .collect(Collectors.toList());
    }

    public ILoggerConfig save(ILoggerConfig loggerConfig) {
        String name = loggerConfig.getName();
        LOGGER_CONFIGS.put(name, SerializationUtils.clone(loggerConfig));
        return LOGGER_CONFIGS.get(name);
    }

    public void delete(String loggerConfigName) {
        LOGGER_CONFIGS.remove(loggerConfigName);
    }
}
