package com.github.jvanheesch.implementations;

import com.github.jvanheesch.interfaces.ILoggerConfig;
import com.github.jvanheesch.interfaces.LoggerConfigService;

class LoggerConfigServiceImpl implements LoggerConfigService {
    private final LoggerConfigDao loggerConfigDao;

    LoggerConfigServiceImpl(LoggerConfigDao loggerConfigDao) {
        this.loggerConfigDao = loggerConfigDao;
    }

    @Override
    public void createOrUpdate(ILoggerConfig logger) {
        if (logger == null || logger.getName() == null || logger.getLevel() == null) {
            throw new IllegalArgumentException();
        }

        this.loggerConfigDao.save(logger);
    }

    @Override
    public void delete(String name) {
        this.loggerConfigDao.delete(name);
    }
}
