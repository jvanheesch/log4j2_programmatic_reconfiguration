package com.github.jvanheesch;

import com.github.jvanheesch.interfaces.IAppenderRef;
import com.github.jvanheesch.interfaces.ILoggerConfig;
import com.github.jvanheesch.interfaces.Log4jReadService;
import com.github.jvanheesch.interfaces.LoggerConfigService;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;
import java.util.stream.Collectors;

class AppenderRefDeletePanel extends Panel {
    private static final long serialVersionUID = 5591486142986002753L;

    @SpringBean
    private Log4jReadService log4jReadService;
    @SpringBean
    private LoggerConfigService loggerConfigService;

    private final IModel<String> loggerConfigNameModel;
    private final IModel<String> appenderNameModel;

    AppenderRefDeletePanel(String id) {
        super(id);

        this.loggerConfigNameModel = new Model<>();
        this.appenderNameModel = new Model<>();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Form<?> form = new Form<Void>("form") {
            private static final long serialVersionUID = 3275477528957660820L;

            @Override
            protected void onSubmit() {
                super.onSubmit();

                ILoggerConfig loggerConfig = AppenderRefDeletePanel.this.log4jReadService.find(AppenderRefDeletePanel.this.loggerConfigNameModel.getObject());

                List<IAppenderRef> appenderRefs = loggerConfig.getAppenderRefs().stream()
                        .filter(appenderRef -> !appenderRef.getAppenderName().equals(AppenderRefDeletePanel.this.appenderNameModel.getObject()))
                        .collect(Collectors.toList());

                loggerConfig.setAppenderRefs(appenderRefs);

                AppenderRefDeletePanel.this.loggerConfigService.createOrUpdate(loggerConfig);
                AppenderRefDeletePanel.this.loggerConfigNameModel.setObject(null);
                AppenderRefDeletePanel.this.appenderNameModel.setObject(null);
            }
        };
        this.add(form);

        form.add(new TextField<>("loggerConfigName", this.loggerConfigNameModel).setConvertEmptyInputStringToNull(false));
        form.add(new TextField<>("appenderName", this.appenderNameModel));

        form.add(new SubmitLink("submitLink"));
    }
}
