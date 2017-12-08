package com.github.jvanheesch.interfaces;

import java.util.List;

public interface Log4jReadService {
    List<ILoggerConfig> findAllLoggerConfigs();

    List<IAppender> findAllAppenders();

    ILoggerConfig find(String loggerConfigName);
}
