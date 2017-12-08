package com.github.jvanheesch.implementations;

import com.github.jvanheesch.interfaces.ILoggerConfig;

import java.util.List;

interface LoggerConfigDao {
    ILoggerConfig read(String loggerConfigName);

    List<ILoggerConfig> findAll();

    ILoggerConfig save(ILoggerConfig loggerConfig);

    void delete(String loggerConfigName);
}
