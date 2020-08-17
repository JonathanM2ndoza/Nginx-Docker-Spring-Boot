package com.jmendoza.wallet.configuration.tasks.notification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class TaskExecutorEmail {

    private static final Logger loggerException = LogManager.getLogger(TaskExecutorEmail.class);

    @Bean(name = "emailExecutor")
    public TaskExecutor emailExecutor(Environment env) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix(env.getRequiredProperty("task.executor.email.threadNamePrefix"));
        threadPoolTaskExecutor.setCorePoolSize(Integer.parseInt(env.getRequiredProperty("task.executor.email.corePoolSize")));
        threadPoolTaskExecutor.setMaxPoolSize(Integer.parseInt(env.getRequiredProperty("task.executor.email.maxPoolSize")));
        threadPoolTaskExecutor.setQueueCapacity(Integer.parseInt(env.getRequiredProperty("task.executor.email.queueCapacity")));
        threadPoolTaskExecutor.afterPropertiesSet();
        loggerException.debug("SET - {}", env.getRequiredProperty("task.executor.email.threadNamePrefix"));
        return threadPoolTaskExecutor;
    }
}
