package com.github.jvanheesch.implementations;

import com.github.jvanheesch.interfaces.IAppenderRef;
import org.apache.logging.log4j.Level;

public final class AppenderRefImpl implements IAppenderRef {
    private static final long serialVersionUID = 7708137713809377172L;

    private String appenderName;
    private Level level;

    @Override
    public String getAppenderName() {
        return this.appenderName;
    }

    @Override
    public void setAppenderName(String appenderName) {
        this.appenderName = appenderName;
    }

    @Override
    public Level getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(Level level) {
        this.level = level;
    }
}
