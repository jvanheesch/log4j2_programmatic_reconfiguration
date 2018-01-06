package com.github.jvanheesch.implementations;

import com.github.jvanheesch.Log4jUtil;
import com.github.jvanheesch.interfaces.ILoggerConfig;
import com.github.jvanheesch.interfaces.Log4jReadService;
import com.github.jvanheesch.interfaces.LoggerConfigReadService;
import com.github.jvanheesch.interfaces.LoggerConfigService;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Reconfigurable;
import org.apache.logging.log4j.core.config.composite.CompositeConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Configuration
@EnableAspectJAutoProxy
public class LoggerSpringConfig {

    @Bean
    public static Log4jReadService log4jReadService() {
        return new Log4jReadServiceImpl();
    }

    @Bean
    public static LoggerConfigDao loggerConfigDao() {
        return new LoggerConfigDaoImpl();
    }

    @Bean
    public static LoggerConfigService loggerConfigService(LoggerConfigDao loggerConfigDao) {
        return new LoggerConfigServiceImpl(loggerConfigDao);
    }

    @Bean
    public static LoggerConfigReadService loggerConfigReadService(LoggerConfigDao loggerConfigDao) {
        return new LoggerConfigReadServiceImpl(loggerConfigDao);
    }

    @Bean
    public static Log4jReinitAspect log4jReinitAspect(Reconfigurable reconfigurable) {
        return new Log4jReinitAspect(reconfigurable);
    }

    @Bean
    public Reconfigurable xmlBasedConfiguration(
            @Value("/com/github/jvanheesch/init_log4j2_part1.xml") String part1,
            @Value("/com/github/jvanheesch/init_log4j2_part2.xml") String part2
    ) {
        Optional<URI> defaultXml = this.getResourceUri(part1);
        Optional<URI> specificXml = this.getResourceUri(part2);

        List<AbstractConfiguration> configurations = new ArrayList<>();

        defaultXml.ifPresent(loadLogConfig(configurations));
        specificXml.ifPresent(loadLogConfig(configurations));

        return new CompositeConfiguration(configurations);
    }

    @Bean
    public Reconfigurable dbBasedConfiguration(LoggerConfigReadService loggerConfigReadService) {
        return () -> {
            List<ILoggerConfig> allFromDb = loggerConfigReadService.findAllFromDb();
            return Log4jUtil.constructConfiguration(allFromDb, "dbBasedConfiguration");
        };
    }

    /**
     * See {@link org.apache.logging.log4j.core.config.composite.DefaultMergeStrategy}.
     * <p>
     * Log4j has the ability to automatically configure itself during initialization.
     * We initialize log4j2 based on xml config only, and re-initialize it later when all necessary spring beans have been created.
     */
    @Bean
    public Reconfigurable reconfigurable(
            Reconfigurable xmlBasedConfiguration,
            Reconfigurable dbBasedConfiguration
    ) {
        return () -> new CompositeConfiguration(
                Arrays.asList(
                        (AbstractConfiguration) xmlBasedConfiguration.reconfigure(),
                        (AbstractConfiguration) dbBasedConfiguration.reconfigure()
                )
        );
    }

    private Optional<URI> getResourceUri(String resource) {
        return Optional.ofNullable(this.getClass().getResource(resource))
                .map(url -> {
                    URI uri;
                    try {
                        uri = url.toURI();
                    } catch (URISyntaxException e) {
                        throw new RuntimeException();
                    }
                    return uri;
                });
    }

    private static Consumer<URI> loadLogConfig(List<AbstractConfiguration> configurations) {
        return xml -> configurations.add((AbstractConfiguration) ConfigurationFactory.getInstance().getConfiguration(LoggerContext.getContext(false), ConfigurationSource.fromUri(xml)));
    }
}
