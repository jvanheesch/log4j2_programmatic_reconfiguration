package com.github.jvanheesch;

import com.github.jvanheesch.interfaces.IAppender;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.Arrays;
import java.util.List;

class AppenderTablePanel extends GenericPanel<List<IAppender>> {
    private static final long serialVersionUID = -7496097113286940349L;

    AppenderTablePanel(String id, IModel<List<IAppender>> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        DataTable<IAppender, String> dataTable = new DataTable<>(
                "dataTable",
                Arrays.asList(new PropertyColumn<>(Model.of("Name"), "name")),
                new NonRetardedListDataProvider<>(this.getModel()),
                10
        );

        dataTable.addTopToolbar(new HeadersToolbar<>(dataTable, null));
        this.add(dataTable);
    }
}
