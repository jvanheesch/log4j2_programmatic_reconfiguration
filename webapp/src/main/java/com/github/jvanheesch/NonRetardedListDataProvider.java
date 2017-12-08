package com.github.jvanheesch;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Based on {@link org.apache.wicket.markup.repeater.data.ListDataProvider}, but less retarded.
 */
public class NonRetardedListDataProvider<T extends Serializable> implements IDataProvider<T> {
    private static final long serialVersionUID = 1L;

    private final IModel<List<T>> model;

    public NonRetardedListDataProvider(IModel<List<T>> model) {
        if (model == null) {
            throw new IllegalArgumentException("argument [list] cannot be null");
        }

        this.model = model;
    }

    protected List<T> getData() {
        return this.model.getObject();
    }

    @Override
    public Iterator<T> iterator(long first, long count) {
        List<T> list = this.getData();

        long toIndex = first + count;
        if (toIndex > list.size()) {
            toIndex = list.size();
        }
        return list.subList((int) first, (int) toIndex).listIterator();
    }

    @Override
    public long size() {
        return this.getData().size();
    }

    @Override
    public IModel<T> model(T object) {
        return new Model<>(object);
    }

    @Override
    public void detach() {
        this.model.detach();
    }
}
