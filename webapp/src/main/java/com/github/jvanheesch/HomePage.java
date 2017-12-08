package com.github.jvanheesch;

import com.github.jvanheesch.model.AppenderListModel;
import com.github.jvanheesch.model.LoggerConfigListModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
    private static final long serialVersionUID = -8896769873107976128L;

    private static final Logger LOG = LogManager.getLogger();

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.add(new LoggerConfigTablePanel("loggerConfigTablePanel", new LoggerConfigListModel()));

        this.add(new AppenderTablePanel("appenderTablePanel", new AppenderListModel()));

        this.add(new LoggerConfigCreateUpdatePanel("loggerConfigPanel"));

        this.add(new LoggerConfigDeletePanel("loggerConfigDeletePanel"));

        this.add(new AppenderRefCreateUpdatePanel("appenderRefCreateUpdatePanel"));

        this.add(new AppenderRefDeletePanel("appenderRefDeletePanel"));
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();

        LOG.info("HomePage - onConfigure()");
    }
}
