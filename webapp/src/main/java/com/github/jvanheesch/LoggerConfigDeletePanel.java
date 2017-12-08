package com.github.jvanheesch;

import com.github.jvanheesch.interfaces.LoggerConfigService;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

class LoggerConfigDeletePanel extends GenericPanel<String> {
    private static final long serialVersionUID = 5591486142986002753L;

    @SpringBean
    private LoggerConfigService loggerConfigService;

    LoggerConfigDeletePanel(String id) {
        super(id, new Model<>());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Form<?> form = new Form<Void>("form") {
            private static final long serialVersionUID = 3275477528957660820L;

            @Override
            protected void onSubmit() {
                super.onSubmit();

                IModel<String> model = LoggerConfigDeletePanel.this.getModel();
                LoggerConfigDeletePanel.this.loggerConfigService.delete(model.getObject());
                model.setObject(null);
            }
        };
        this.add(form);

        form.add(new TextField<>("name", this.getModel()).setConvertEmptyInputStringToNull(false));

        form.add(new SubmitLink("submitLink"));
    }
}
