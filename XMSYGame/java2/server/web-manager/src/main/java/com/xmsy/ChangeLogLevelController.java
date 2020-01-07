package com.xmsy;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

import com.xmsy.server.zxyy.manager.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/changeLogLevel")
@Slf4j
public class ChangeLogLevelController {

    @RequestMapping(value = "/logLevelToInfo", method = RequestMethod.GET)
    @ResponseBody
    public R logLevelToInfo() {

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = loggerContext.getLogger("root");
        ((Logger) logger).setLevel(Level.INFO);
        log.info("============================================修改日志等级为info级别");
        log.info("============================================修改日志等级为info级别");

        log.debug("============================================日志等级为debug级别");
        log.debug("============================================日志等级为debug级别");
        return R.ok();
    }


    @RequestMapping(value = "/logLevelToDebug", method = RequestMethod.GET)
    @ResponseBody
    public R logLevelToDebug() {

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = loggerContext.getLogger("root");
        ((Logger) logger).setLevel(Level.DEBUG);
        log.debug("============================================修改日志等级为debug级别");
        log.debug("============================================修改日志等级为debug级别");


        return R.ok();
    }
}
