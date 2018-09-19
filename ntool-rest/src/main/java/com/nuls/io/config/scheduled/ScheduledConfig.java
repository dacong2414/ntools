package com.nuls.io.config.scheduled;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 开启定时任务
 *
 * @author hhu 【huan.hu@cnambition.com】
 * @version com.ambition.imarad.config.scheduled, v 0.1 2017/8/30 17:30 hhu Exp $$
 */
@Configuration
@EnableAsync
@EnableScheduling
public class ScheduledConfig {
}
