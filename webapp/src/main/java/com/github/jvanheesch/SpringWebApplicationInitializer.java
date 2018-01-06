package com.github.jvanheesch;

import com.github.jvanheesch.implementations.LoggerSpringConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.protocol.http.WicketFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import java.util.EnumSet;

public class SpringWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger LOG = LogManager.getLogger();

    private static final String LOG_FOLDER = System.getProperty("user.dir") + "/src/main/java/com/github/jvanheesch/logs";

    @Override
    public void onStartup(ServletContext servletContext) {
        LOG.info("SpringWebApplicationInitializer.onStartup");

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
     * Too late for log4j initialization, but in time for re-initialization by Log4jReinitAspect's @PostConstruct.
     */
    private static void setLog4j2SystemProperties() {
        System.setProperty("log4j2.logdirectory", LOG_FOLDER);
    }
}
