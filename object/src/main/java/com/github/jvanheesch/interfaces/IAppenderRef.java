package com.github.jvanheesch.interfaces;

import com.github.jvanheesch.implementations.AppenderRefImpl;
import com.mycompany.builder.Builder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.AppenderRef;

import java.io.Serializable;

public interface IAppenderRef extends Serializable {
    String getAppenderName();

    void setAppenderName(String appenderName);

    Level getLevel();

    void setLevel(Level level);

    static IAppenderRef of(AppenderRef appenderRef) {
        return builder()
                .with(IAppenderRef::setAppenderName, appenderRef.getRef())
                .with(IAppenderRef::setLevel, appenderRef.getLevel())
                .build();
    }

    static Builder<IAppenderRef> builder() {
        return Builder.of(AppenderRefImpl::new);
    }
}
