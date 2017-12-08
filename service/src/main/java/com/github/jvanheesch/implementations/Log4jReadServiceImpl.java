package com.github.jvanheesch.implementations;

import com.github.jvanheesch.interfaces.IAppender;
import com.github.jvanheesch.interfaces.ILoggerConfig;
import com.github.jvanheesch.interfaces.Log4jReadService;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;

import java.util.List;
import java.util.stream.Collectors;

class Log4jReadServiceImpl implements Log4jReadService {
    @Override
    public List<ILoggerConfig> findAllLoggerConfigs() {
        Configuration config = this.getConfiguration();

        return config.getLoggers().values()
                .stream()
                .map(ILoggerConfig::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<IAppender> findAllAppenders() {
        Configuration config = this.getConfiguration();

        return config.getAppenders().values()
                .stream()
                .map(IAppender::of)
                .collect(Collectors.toList());
    }

    @Override
    public ILoggerConfig find(String loggerConfigName) {
        return ILoggerConfig.of(this.getConfiguration().getLoggerConfig(loggerConfigName));
    }

    private Configuration getConfiguration() {
        LoggerContext ctx = LoggerContext.getContext(false);
        return ctx.getConfiguration();
    }
}
