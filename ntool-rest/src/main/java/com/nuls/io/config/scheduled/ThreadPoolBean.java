package com.nuls.io.config.scheduled;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 新开线程默认在service层有事务
 *
 * @author ambition
 * @version $Id: ThreadPoolBean.java, v 0.1 2018年1月9日 下午3:44:08 ambition Exp $
 */
@Configuration
@EnableAsync
public class ThreadPoolBean {
    private static int corePoolSize       = 1;
    private static int maximumPoolSize    = Runtime.getRuntime().availableProcessors() + 1;
    private static int keepAliveTime      = 120;
    private static int arrayBlockingQueue = 2000;

    @Bean
    public Executor myExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maximumPoolSize);
        executor.setQueueCapacity(arrayBlockingQueue);
        executor.setThreadNamePrefix("ThreadPoolBean-");
        //既不抛弃任务也不抛出异常，直接运行任务的run方法，换言之将任务回退给调用者来直接运行。使用该策略时线程池饱和后将由调用线程池的主线程自己来执行任务，因此在执行任务的这段时间里主线程无法再提交新任务，从而使线程池中工作线程有时间将正在处理的任务处理完成。
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setKeepAliveSeconds(keepAliveTime);
        executor.initialize();
        return executor;
    }
}