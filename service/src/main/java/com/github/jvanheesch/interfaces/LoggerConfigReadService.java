package com.github.jvanheesch.interfaces;

import java.util.List;

public interface LoggerConfigReadService {
    List<ILoggerConfig> findAllFromDb();
}
