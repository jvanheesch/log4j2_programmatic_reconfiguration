package com.github.jvanheesch;

import com.github.jvanheesch.interfaces.IAppenderRef;
import com.github.jvanheesch.interfaces.ILoggerConfig;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.Arrays;
import java.util.List;

class LoggerConfigTablePanel extends GenericPanel<List<ILoggerConfig>> {
    private static final long serialVersionUID = -7496097113286940349L;

    LoggerConfigTablePanel(String id, IModel<List<ILoggerConfig>> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        DataTable<ILoggerConfig, String> dataTable = new DataTable<>(
                "dataTable",
                Arrays.asList(
                        new PropertyColumn<>(Model.of("Name"), "displayName"),
                        new PropertyColumn<>(Model.of("Level"), "level"),
                        new PropertyColumn<>(Model.of("Additive"), "additive"),
                        new AppenderRefsColumn(Model.of("AppenderRefs"))
                ),
                new NonRetardedListDataProvider<>(this.getModel()),
                10
        );

        dataTable.addTopToolbar(new HeadersToolbar<>(dataTable, null));
        this.add(dataTable);
    }

    private static class AppenderRefsColumn extends AbstractColumn<ILoggerConfig, String> {
        private static final long serialVersionUID = 1865763936058370158L;

        AppenderRefsColumn(IModel<String> displayModel) {
            super(displayModel);
        }

        @Override
        public void populateItem(Item<ICellPopulator<ILoggerConfig>> cellItem, String componentId, IModel<ILoggerConfig> rowModel) {
            RepeatingView rv = new RepeatingView(componentId);
            for (IAppenderRef appenderRef : rowModel.getObject().getAppenderRefs()) {
                rv.add(new AppenderRefSummaryPanel(rv.newChildId(), Model.of(appenderRef)));
            }

            cellItem.add(rv);
        }
    }

    private static class AppenderRefSummaryPanel extends GenericPanel<IAppenderRef> {
        private static final long serialVersionUID = 5934568102952927660L;

        AppenderRefSummaryPanel(String id, IModel<IAppenderRef> model) {
            super(id, model);
        }

        @Override
        protected void onInitialize() {
            super.onInitialize();

            this.add(new Label("appenderName", new PropertyModel<>(this.getModel(), "appenderName")));
            this.add(new Label("level", new PropertyModel<>(this.getModel(), "level")));
        }
    }
}
