package com.github.jvanheesch.interfaces;

import com.github.jvanheesch.implementations.AppenderImpl;
import com.github.jvanheesch.builder.Builder;
import org.apache.logging.log4j.core.Appender;

import java.io.Serializable;

public interface IAppender extends Serializable {
    String getName();

    void setName(String name);

    static IAppender of(Appender appender) {
        return builder()
                .with(IAppender::setName, appender.getName())
                .build();
    }

    static Builder<IAppender> builder() {
        return Builder.of(AppenderImpl::new);
    }
}
