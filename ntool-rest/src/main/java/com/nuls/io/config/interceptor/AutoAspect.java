
package com.nuls.io.config.interceptor;

import java.util.Properties;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.nuls.io.config.datasource.DruidStatProperties;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableConfigurationProperties(DruidStatProperties.class)
public class AutoAspect {
    @Autowired
    DruidStatProperties druidStatProperties;

    public DruidStatInterceptor druidStatInterceptor() {
        return new DruidStatInterceptor();
    }

    public JdkRegexpMethodPointcut jdkRegexpMethodPointcut() {
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPatterns(druidStatProperties.getAopPatterns());
        return pointcut;
    }

    /**
     * 通过拦截器druid能获取统计spring的信息
     * @param properties
     * @return
     */
    @Bean
    public Advisor advisor(DruidStatProperties properties) {
        return new DefaultPointcutAdvisor(jdkRegexpMethodPointcut(), druidStatInterceptor());
    }

    @Bean
    public TransactionInterceptor transactionInterceptor(DataSourceTransactionManager  transactionManager) {
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor
            .setTransactionManager((PlatformTransactionManager) transactionManager);

        //回滚为-，不回滚为+
        Properties props = new Properties();
        props.setProperty("get*",
            "PROPAGATION_REQUIRED,ISOLATION_DEFAULT,timeout_20,readOnly,-RollbackException");
        props.setProperty("exe*",
            "PROPAGATION_REQUIRED,ISOLATION_DEFAULT,timeout_20,-RollbackException");
        props.setProperty("sav*",
            "PROPAGATION_REQUIRED,ISOLATION_DEFAULT,timeout_20,-RollbackException");
        props.setProperty("vali*",
            "PROPAGATION_REQUIRED,ISOLATION_DEFAULT,timeout_20,-RollbackException");
        props.setProperty("init*",
            "PROPAGATION_REQUIRED,ISOLATION_DEFAULT,timeout_20,-RollbackException");
        props.setProperty("add*",
            "PROPAGATION_REQUIRED,ISOLATION_DEFAULT,timeout_20,-RollbackException");
        props.setProperty("do*",
            "PROPAGATION_REQUIRED,ISOLATION_DEFAULT,timeout_20,-RollbackException");
        props.setProperty("set*",
            "PROPAGATION_REQUIRED,ISOLATION_DEFAULT,timeout_20,-RollbackException");
        props.setProperty("send*",
            "PROPAGATION_REQUIRED,ISOLATION_DEFAULT,timeout_20,-RollbackException");
        props.setProperty("reset*",
            "PROPAGATION_REQUIRED,ISOLATION_DEFAULT,timeout_20,-RollbackException");
        props.setProperty("insert*",
            "PROPAGATION_REQUIRED,ISOLATION_DEFAULT,timeout_20,-RollbackException");
        props.setProperty("mod*",
            "PROPAGATION_REQUIRED,ISOLATION_DEFAULT,timeout_20,-RollbackException");
        props.setProperty("process*",
            "PROPAGATION_REQUIRED,ISOLATION_DEFAULT,timeout_20,-RollbackException");
        props.setProperty("updat*",
            "PROPAGATION_REQUIRED,ISOLATION_DEFAULT,timeout_20,-RollbackException");
        props.setProperty("del*",
            "PROPAGATION_REQUIRED,ISOLATION_DEFAULT,timeout_20,-RollbackException");
        transactionInterceptor.setTransactionAttributes(props);

        return transactionInterceptor;
    }

    @Bean
    public BeanNameAutoProxyCreator proxyFactory() {
        BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
        creator.setInterceptorNames("transactionInterceptor");
        creator.setBeanNames("*Dao");
        creator.setBeanNames("*DaoImpl");
        creator.setBeanNames("*Service");
        creator.setBeanNames("*ServiceImpl");
        return creator;
    }

}