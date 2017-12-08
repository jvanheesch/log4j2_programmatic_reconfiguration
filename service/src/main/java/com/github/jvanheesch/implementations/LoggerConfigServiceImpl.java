package com.github.jvanheesch.implementations;

import com.github.jvanheesch.interfaces.ILoggerConfig;
import com.github.jvanheesch.interfaces.LoggerConfigService;

class LoggerConfigServiceImpl implements LoggerConfigService {
    @Override
    public void createOrUpdate(ILoggerConfig logger) {
        if (logger == null || logger.getName() == null || logger.getLevel() == null) {
            throw new IllegalArgumentException();
        }

        LoggerConfigDao.save(logger);
    }

    @Override
    public void delete(String name) {
        LoggerConfigDao.delete(name);
    }
}
