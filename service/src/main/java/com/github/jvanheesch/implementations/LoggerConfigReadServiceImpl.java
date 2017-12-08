package com.github.jvanheesch.implementations;

import com.github.jvanheesch.interfaces.ILoggerConfig;
import com.github.jvanheesch.interfaces.LoggerConfigReadService;

import java.util.List;

class LoggerConfigReadServiceImpl implements LoggerConfigReadService {
    private final LoggerConfigDao loggerConfigDao;

    LoggerConfigReadServiceImpl(LoggerConfigDao loggerConfigDao) {
        this.loggerConfigDao = loggerConfigDao;
    }

    @Override
    public List<ILoggerConfig> findAllFromDb() {
        return this.loggerConfigDao.findAll();
    }
}
