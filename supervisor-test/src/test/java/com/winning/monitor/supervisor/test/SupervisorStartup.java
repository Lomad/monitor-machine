package com.winning.monitor.supervisor.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by nicholasyan on 16/9/9.
 */
public class SupervisorStartup {

    public static final String DEFAULT_SPRING_CONFIG = "classpath*:META-INF/spring/*.xml";
    private static volatile boolean running = true;
    private static Logger logger = LoggerFactory.getLogger(SupervisorStartup.class);


    private static void addShutdownHook(final ClassPathXmlApplicationContext applicationContext) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    logger.info("系统正在关闭......");
                    //
                    //处理关闭
                    //
                    if (applicationContext != null) {
                        applicationContext.stop();
                        applicationContext.close();
                    }
                    logger.info("系统已安全关闭!");
                    Thread.sleep(1000);
                } catch (Throwable t) {
                    logger.error(t.getMessage(), t);
                }
                synchronized (SupervisorStartup.class) {
                    running = false;
                    SupervisorStartup.class.notify();
                }
            }
        });
    }

    public static void main(String[] args) {

        logger.info("系统正在启动.......");


        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(DEFAULT_SPRING_CONFIG);

        applicationContext.start();

        addShutdownHook(applicationContext);

        logger.info("系统启动完成!");

        synchronized (SupervisorStartup.class) {
            while (running) {
                try {
                    SupervisorStartup.class.wait();
                } catch (Throwable e) {
                }
            }
        }
    }


}
