package com.github.jvanheesch.model;

import com.github.jvanheesch.interfaces.IAppender;
import com.github.jvanheesch.interfaces.Log4jReadService;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class AppenderListModel extends LoadableDetachableModel<List<IAppender>> {
    private static final long serialVersionUID = 5457485390791754869L;

    @SpringBean
    private Log4jReadService log4jReadService;

    public AppenderListModel() {
        Injector.get().inject(this);
    }

    @Override
    protected List<IAppender> load() {
        return this.log4jReadService.findAllAppenders();
    }
}
