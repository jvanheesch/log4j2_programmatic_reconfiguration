package com.github.jvanheesch;

import com.github.jvanheesch.interfaces.ILoggerConfig;
import com.github.jvanheesch.interfaces.LoggerConfigService;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Arrays;

class LoggerConfigCreateUpdatePanel extends GenericPanel<ILoggerConfig> {
    private static final long serialVersionUID = -3246768467993278694L;

    @SpringBean
    private LoggerConfigService loggerConfigService;

    LoggerConfigCreateUpdatePanel(String id) {
        super(id, Model.of(ILoggerConfig.builder().build()));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Form<?> form = new Form<Void>("form") {
            private static final long serialVersionUID = 3275477528957660820L;

            @Override
            protected void onSubmit() {
                super.onSubmit();

                IModel<ILoggerConfig> model = LoggerConfigCreateUpdatePanel.this.getModel();
                LoggerConfigCreateUpdatePanel.this.loggerConfigService.createOrUpdate(model.getObject());
                model.setObject(ILoggerConfig.builder().build());
            }
        };
        this.add(form);

        form.add(new TextField<>("name", new PropertyModel<>(this.getModel(), "name")).setConvertEmptyInputStringToNull(false));
        form.add(new DropDownChoice<>("level", new PropertyModel<>(this.getModel(), "level"), Model.ofList(ILoggerConfig.LEVELS)).setRequired(true));
        form.add(new DropDownChoice<>("additive", new PropertyModel<>(this.getModel(), "additive"), Model.ofList(Arrays.asList(Boolean.TRUE, Boolean.FALSE))));

        form.add(new SubmitLink("submitLink"));
    }
}
