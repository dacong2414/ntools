package com.nuls.io.config.datasource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.LogFilter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;

@Configuration
public class FiltersConfig {

    @Bean
    public LogFilter logFilter() {
        Slf4jLogFilter logFilter = new Slf4jLogFilter();
        logFilter.setStatementExecutableSqlLogEnable(false);
        return logFilter;
    }

    @Bean
    public Filter statFilter() {
        StatFilter statFilter = new StatFilter();
        statFilter.setSlowSqlMillis(10000);
        statFilter.setLogSlowSql(true);
        return statFilter;
    }

    @Bean
    public Filter wallFilter() {
        WallFilter filter = new WallFilter();
        filter.setDbType("mysql");
        filter.setConfig(wallConfig());
        return filter;
    }

    public WallConfig wallConfig() {
        WallConfig config = new WallConfig();
        config.setDir("META-INF/druid/wall/mysql");
        return config;
    }
}