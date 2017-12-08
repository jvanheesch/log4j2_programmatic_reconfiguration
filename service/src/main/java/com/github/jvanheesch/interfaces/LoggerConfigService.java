package com.github.jvanheesch.interfaces;

public interface LoggerConfigService {
    void createOrUpdate(ILoggerConfig loggerConfig);

    void delete(String name);
}
