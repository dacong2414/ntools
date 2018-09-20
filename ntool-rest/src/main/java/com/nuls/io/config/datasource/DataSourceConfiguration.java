package com.nuls.io.config.datasource;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.LogFilter;
import com.alibaba.druid.pool.DruidDataSource;

/**
 * 
 * @author USER
 *
 */
@Configuration
public class DataSourceConfiguration {

    @Primary
    @Bean(initMethod = "init", destroyMethod = "close" ,name="dataSource")
    public DataSource dataSource(Environment env, LogFilter logFilter, Filter statFilter,
                                 Filter wallFilter) {
        /**
         * 添加sql防火墙、druid监控、spring dao、service调用统计
         */
        List<Filter> proxyFilters = new ArrayList<>();
        proxyFilters.add(wallFilter);
        proxyFilters.add(statFilter);
        proxyFilters.add(logFilter);
        DruidDataSource druid = new DruidDataSource();
        druid.setUrl(env.getProperty("spring.datasource.url"));
        druid.setUsername(env.getProperty("spring.datasource.username"));
        druid.setPassword(env.getProperty("spring.datasource.password"));
        druid.setInitialSize(Integer.parseInt(env.getProperty("spring.datasource.initialSize")));
        druid.setMaxActive(Integer.parseInt(env.getProperty("spring.datasource.maxActive")));
        druid.setMinIdle(Integer.parseInt(env.getProperty("spring.datasource.minIdle")));
        druid.setMaxWait(Integer.parseInt(env.getProperty("spring.datasource.maxWait")));
        druid.setRemoveAbandoned(Boolean.parseBoolean(env
            .getProperty("spring.datasource.removeAbandoned")));
        druid.setRemoveAbandonedTimeout(Integer.parseInt(env
            .getProperty("spring.datasource.removeAbandonedTimeout")));
        druid.setDefaultAutoCommit(Boolean.parseBoolean(env
            .getProperty("spring.datasource.defaultAutoCommit")));
        druid.setLogAbandoned(Boolean.parseBoolean(env
            .getProperty("spring.datasource.logAbandoned")));
        druid.setTestWhileIdle(Boolean.parseBoolean(env
            .getProperty("spring.datasource.testWhileIdle")));
        druid.setTestOnBorrow(Boolean.parseBoolean(env
            .getProperty("spring.datasource.testOnBorrow")));
        druid.setTestOnReturn(Boolean.parseBoolean(env
            .getProperty("spring.datasource.testOnReturn")));
        druid.setValidationQuery(env.getProperty("spring.datasource.validationQuery"));
        druid.setValidationQueryTimeout(Integer.parseInt(env
            .getProperty("spring.datasource.validationQueryTimeout")));
        druid.setTimeBetweenEvictionRunsMillis(Integer.parseInt(env
            .getProperty("spring.datasource.timeBetweenEvictionRunsMillis")));
        druid.setMinEvictableIdleTimeMillis(Integer.parseInt(env
            .getProperty("spring.datasource.minEvictableIdleTimeMillis")));
        druid.setPoolPreparedStatements(Boolean.parseBoolean(env
            .getProperty("spring.datasource.poolPreparedStatements")));
        druid.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(env
            .getProperty("spring.datasource.maxPoolPreparedStatementPerConnectionSize")));
        druid.setProxyFilters(proxyFilters);
        maybeGetDriverClassName(druid);
        return druid;
    }

    /**
     * 通过url头部节点判断出对应的数据库类型
     * @param druid
     */
    private void maybeGetDriverClassName(DruidDataSource druid) {
        if (StringUtils.isEmpty(druid.getDriverClassName())
            && StringUtils.isNotEmpty(druid.getUrl())) {
            String driverClass = DatabaseDriver.fromJdbcUrl(druid.getUrl()).getDriverClassName();
            if (StringUtils.isNotEmpty(driverClass)) {
                druid.setDriverClassName(driverClass);
            }
        }
    }
}