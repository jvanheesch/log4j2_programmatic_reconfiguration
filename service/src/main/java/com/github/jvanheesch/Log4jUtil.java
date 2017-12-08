package com.github.jvanheesch;

import com.github.jvanheesch.interfaces.IAppenderRef;
import com.github.jvanheesch.interfaces.ILoggerConfig;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.builder.api.AppenderRefComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.LoggableComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.builder.impl.DefaultConfigurationBuilder;

import java.util.Collection;

public final class Log4jUtil {
    private Log4jUtil() {
    }

    @SuppressWarnings("IfMayBeConditional")
    public static BuiltConfiguration constructConfiguration(Collection<ILoggerConfig> loggerConfigs, String configName) {
        ConfigurationBuilder<BuiltConfiguration> builder = new DefaultConfigurationBuilder<>()
                .setConfigurationName(configName);

        for (ILoggerConfig loggerConfig : loggerConfigs) {
            LoggableComponentBuilder<?> loggableComponentBuilder;
            if (loggerConfig.getName().equals(ILoggerConfig.ROOT_LOGGER_NAME)) {
                loggableComponentBuilder = builder.newRootLogger(loggerConfig.getLevel());
            } else {
                loggableComponentBuilder = builder.newLogger(loggerConfig.getName(), loggerConfig.getLevel())
                        .addAttribute("additivity", loggerConfig.isAdditive());
            }

            for (IAppenderRef appenderRef : loggerConfig.getAppenderRefs()) {
                AppenderRefComponentBuilder appenderRefComponentBuilder = builder.newAppenderRef(appenderRef.getAppenderName());
                Level level = appenderRef.getLevel();
                // prevent NPE in addAttribute's level.toString();
                if (level != null) {
                    appenderRefComponentBuilder
                            .addAttribute("level", level);
                }
                loggableComponentBuilder.add(appenderRefComponentBuilder);
            }

            if (loggableComponentBuilder instanceof LoggerComponentBuilder) {
                builder.add((LoggerComponentBuilder) loggableComponentBuilder);
            } else {
                builder.add((RootLoggerComponentBuilder) loggableComponentBuilder);
            }
        }

        return builder.build(false);
    }
}
