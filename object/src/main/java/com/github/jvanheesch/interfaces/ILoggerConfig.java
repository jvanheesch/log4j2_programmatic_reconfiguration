package com.github.jvanheesch.interfaces;

import com.github.jvanheesch.implementations.LoggerConfigImpl;
import com.mycompany.builder.Builder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.LoggerConfig;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ILoggerConfig extends Serializable {
    List<Level> LEVELS = Collections.unmodifiableList(Stream.of(Level.values()).sorted().collect(Collectors.toList()));

    String ROOT_LOGGER_NAME = LogManager.ROOT_LOGGER_NAME;

    String ROOT_DISPLAY_NAME = LoggerConfig.ROOT;

    String getName();

    void setName(String name);

    Level getLevel();

    void setLevel(Level level);

    boolean isAdditive();

    void setAdditive(boolean additive);

    List<IAppenderRef> getAppenderRefs();

    void setAppenderRefs(List<IAppenderRef> appenderRefs);

    /**
     * This method is reflectively called by a PropertyColumn in LoggerConfigTablePanel.
     */
    @SuppressWarnings("unused")
    default String getDisplayName() {
        String name = this.getName();
        return name.equals(ROOT_LOGGER_NAME) ? ROOT_DISPLAY_NAME : name;
    }

    static ILoggerConfig of(LoggerConfig loggerConfig) {
        return builder()
                .with(ILoggerConfig::setName, loggerConfig.getName())
                .with(ILoggerConfig::setLevel, loggerConfig.getLevel())
                .with(ILoggerConfig::setAdditive, loggerConfig.isAdditive())
                .with(ILoggerConfig::setAppenderRefs, loggerConfig.getAppenderRefs().stream()
                        .map(IAppenderRef::of)
                        .collect(Collectors.toList()))
                .build();
    }

    static Builder<ILoggerConfig> builder() {
        return Builder.of(LoggerConfigImpl::new);
    }
}
