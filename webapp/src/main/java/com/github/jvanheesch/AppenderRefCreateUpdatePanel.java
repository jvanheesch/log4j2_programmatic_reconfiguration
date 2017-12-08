package com.github.jvanheesch;

import com.github.jvanheesch.interfaces.IAppender;
import com.github.jvanheesch.interfaces.IAppenderRef;
import com.github.jvanheesch.interfaces.ILoggerConfig;
import com.github.jvanheesch.interfaces.Log4jReadService;
import com.github.jvanheesch.interfaces.LoggerConfigService;
import com.github.jvanheesch.model.AppenderListModel;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class AppenderRefCreateUpdatePanel extends GenericPanel<IAppenderRef> {
    private static final long serialVersionUID = -3246768467993278694L;

    @SpringBean
    private Log4jReadService log4jReadService;
    @SpringBean
    private LoggerConfigService loggerConfigService;

    private final IModel<String> loggerConfigNameModel;

    AppenderRefCreateUpdatePanel(String id) {
        super(id, Model.of(IAppenderRef.builder().build()));

        this.loggerConfigNameModel = new Model<>();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Form<?> form = new Form<Void>("form") {
            private static final long serialVersionUID = 3275477528957660820L;

            @Override
            protected void onSubmit() {
                super.onSubmit();

                ILoggerConfig loggerConfig = AppenderRefCreateUpdatePanel.this.log4jReadService.find(AppenderRefCreateUpdatePanel.this.loggerConfigNameModel.getObject());

                IModel<IAppenderRef> appenderRefModel = AppenderRefCreateUpdatePanel.this.getModel();

                List<IAppenderRef> appenderRefs = loggerConfig.getAppenderRefs().stream()
                        .filter(appenderRef -> !appenderRef.getAppenderName().equals(appenderRefModel.getObject().getAppenderName()))
                        .collect(Collectors.toCollection(ArrayList::new));

                appenderRefs.add(appenderRefModel.getObject());

                loggerConfig.setAppenderRefs(appenderRefs);

                AppenderRefCreateUpdatePanel.this.loggerConfigService.createOrUpdate(loggerConfig);

                appenderRefModel.setObject(IAppenderRef.builder().build());
                AppenderRefCreateUpdatePanel.this.loggerConfigNameModel.setObject(null);
            }
        };
        this.add(form);

        form.add(new TextField<>("loggerConfigName", this.loggerConfigNameModel).setConvertEmptyInputStringToNull(false));
        form.add(new DropDownChoice<>("appenderName", new PropertyModel<>(this.getModel(), "appenderName"), new AppenderNameListModel()));
        form.add(new DropDownChoice<>("level", new PropertyModel<>(this.getModel(), "level"), Model.ofList(ILoggerConfig.LEVELS)));

        form.add(new SubmitLink("submitLink"));
    }

    private static class AppenderNameListModel extends LoadableDetachableModel<List<String>> {
        private static final long serialVersionUID = -2332467552305329377L;

        private final IModel<List<IAppender>> allAppendersModel = new AppenderListModel();

        @Override
        protected List<String> load() {
            return this.allAppendersModel.getObject().stream()
                    .map(IAppender::getName)
                    .collect(Collectors.toList());
        }
    }
}
