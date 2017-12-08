package com.github.jvanheesch.implementations;

import com.github.jvanheesch.interfaces.IAppender;

public final class AppenderImpl implements IAppender {
    private static final long serialVersionUID = 920260858520918390L;

    private String name;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
