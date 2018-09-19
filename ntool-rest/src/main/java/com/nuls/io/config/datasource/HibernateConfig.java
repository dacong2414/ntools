/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.nuls.io.config.datasource;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hibernate及其事务配置
 * @author hhu
 * @version $Id: HibernateConfig.java, v 0.1 2017年5月10日 上午9:40:15 hhu Exp $
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class HibernateConfig {
    //    private static final Logger logger = Logger.getLogger(HibernateConfig.class);

    @Bean(name = "sessionFactory")
    @DependsOn("flywayInitializer")
    public SessionFactory sessionFactory(DataSource dataSource, Environment env) throws IOException {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(dataSource);
        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        props.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
        props.setProperty("hibernate.use_sql_comments",
                env.getProperty("hibernate.use_sql_comments"));
        bean.setHibernateProperties(props);

        bean.setPhysicalNamingStrategy(PhysicalNamingStrategyStandardImpl.INSTANCE);

        bean.setPackagesToScan(StringUtils.split(env.getProperty("hibernate.packagesToScan"), ","));

        bean.afterPropertiesSet();
        return bean.getObject();
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory)
            throws IOException {
        if (sessionFactory == null) {
            return null;
        }
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    @Bean
    public HibernateTemplate hibernateTemplate(SessionFactory sessionFactory) {
        HibernateTemplate template = new HibernateTemplate();
        template.setSessionFactory(sessionFactory);
        return template;
    }
}
