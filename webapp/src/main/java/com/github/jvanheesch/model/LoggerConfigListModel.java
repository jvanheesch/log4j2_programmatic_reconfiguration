package com.github.jvanheesch.model;

import com.github.jvanheesch.interfaces.ILoggerConfig;
import com.github.jvanheesch.interfaces.Log4jReadService;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class LoggerConfigListModel extends LoadableDetachableModel<List<ILoggerConfig>> {
    private static final long serialVersionUID = 5457485390791754869L;

    @SpringBean
    private Log4jReadService log4jReadService;

    public LoggerConfigListModel() {
        Injector.get().inject(this);
    }

    @Override
    protected List<ILoggerConfig> load() {
        return this.log4jReadService.findAllLoggerConfigs();
    }
}
