package com.github.jvanheesch.implementations;

import com.github.jvanheesch.interfaces.IAppenderRef;
import com.github.jvanheesch.interfaces.ILoggerConfig;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;

public final class LoggerConfigImpl implements ILoggerConfig {
    private static final long serialVersionUID = -2929276306567280473L;

    private String name;
    private Level level;
    private boolean additive;
    private List<IAppenderRef> appenderRefs;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Level getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public boolean isAdditive() {
        return this.additive;
    }

    @Override
    public void setAdditive(boolean additive) {
        this.additive = additive;
    }

    @Override
    public List<IAppenderRef> getAppenderRefs() {
        if (this.appenderRefs == null) {
            this.setAppenderRefs(new ArrayList<>());
        }
        return new ArrayList<>(this.appenderRefs);
    }

    @Override
    public void setAppenderRefs(List<IAppenderRef> appenderRefs) {
        this.appenderRefs = appenderRefs;
    }
}
