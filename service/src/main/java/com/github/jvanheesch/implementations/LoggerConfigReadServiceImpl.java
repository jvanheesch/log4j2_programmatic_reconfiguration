package com.github.jvanheesch.implementations;

import com.github.jvanheesch.interfaces.ILoggerConfig;
import com.github.jvanheesch.interfaces.LoggerConfigReadService;

import java.util.List;

class LoggerConfigReadServiceImpl implements LoggerConfigReadService {
    @Override
    public List<ILoggerConfig> findAllFromDb() {
        return LoggerConfigDao.findAll();
    }
}
