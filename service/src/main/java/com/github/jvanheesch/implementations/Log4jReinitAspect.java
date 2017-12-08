package com.github.jvanheesch.implementations;

import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Reconfigurable;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import javax.annotation.PostConstruct;

@Aspect
class Log4jReinitAspect {
    private final Reconfigurable reconfigurable;

    Log4jReinitAspect(Reconfigurable reconfigurable) {
        this.reconfigurable = reconfigurable;
    }

    @PostConstruct
    @AfterReturning("execution(* com.github.jvanheesch..*LoggerConfigService.*(..)) ")
    private void reinit() {
        LoggerContext ctx = LoggerContext.getContext(false);
        ctx.onChange(this.reconfigurable);
    }
}