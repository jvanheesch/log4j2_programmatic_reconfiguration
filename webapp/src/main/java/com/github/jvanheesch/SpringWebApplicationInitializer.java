package com.github.jvanheesch;

import com.github.jvanheesch.implementations.LoggerSpringConfig;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.wicket.protocol.http.WicketFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public class SpringWebApplicationInitializer implements WebApplicationInitializer {
    private static final List<String> CONFIG_FILES = Arrays.asList(
            "com/github/jvanheesch/init_log4j2_part1.xml",
            "com/github/jvanheesch/init_log4j2_part2.xml"
    );

    private static final String LOG_FOLDER = System.getProperty("user.dir") + "/src/main/java/com/github/jvanheesch/logs";

    @Override
    public void onStartup(ServletContext servletContext) {
        setLog4j2SystemProperties();

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(LoggerSpringConfig.class);
        servletContext.addListener(new ContextLoaderListener(context));

        Dynamic wicketApplicationFilter = servletContext.addFilter("WicketApplication", new WicketFilter());
        wicketApplicationFilter.setInitParameter("applicationClassName", WicketApplication.class.getName());
        wicketApplicationFilter.setInitParameter(WicketFilter.FILTER_MAPPING_PARAM, "/*");
        wicketApplicationFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

    /**
     * Log4j has the ability to automatically configure itself during initialization.
     * We initialize log4j2 based on xml config only, and re-initialize it later when all necessary spring beans have been created.
     */
    private static void setLog4j2SystemProperties() {
        System.setProperty(ConfigurationFactory.CONFIGURATION_FILE_PROPERTY, String.join(", ", CONFIG_FILES));
        System.setProperty("log4j2.logdirectory", LOG_FOLDER);
    }
}
