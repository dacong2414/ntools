package com.nuls.io.config.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import com.nuls.io.common.MyBatisMapper;

/**
 * springboot集成mybatis的基本入口 1）创建数据源(如果采用的是默认的tomcat-jdbc数据源，则不需要)
 * 2）创建SqlSessionFactory 3）配置事务管理器，除非需要使用事务，否则不用配置
 */
@Configuration 
public class MyBatisConfig {
 
  /**
   * 根据数据源创建SqlSessionFactory
   */
  @Bean(name = "sqlSessionFactory")
  @DependsOn("dataSource")
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource,Environment env) throws Exception {
    SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
    fb.setDataSource(dataSource);// 指定数据源(这个必须有，否则报错)
    // 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
    fb.setTypeAliasesPackage(env.getProperty("mybatis.package"));// 指定基包
    fb.setMapperLocations(
        new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapperLocations")));//
 
    return fb.getObject();
  }
  
	@Bean
    public LazyConnectionDataSourceProxy lazyConnectionDataSourceProxy(DataSource dataSource) {
		LazyConnectionDataSourceProxy lazyConnectionDataSourceProxy=new LazyConnectionDataSourceProxy();
		lazyConnectionDataSourceProxy.setTargetDataSource(dataSource);
        return lazyConnectionDataSourceProxy;
    }
	
	
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer(Environment env ) {
		MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage(env.getProperty("mybatis.package"));
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setAnnotationClass(MyBatisMapper.class);
		return mapperScannerConfigurer;
	}
  /**
   * 配置事务管理器
   */
  @Bean
  public DataSourceTransactionManager transactionManager(DataSource dataSource) throws Exception {
    return new DataSourceTransactionManager(dataSource);
  }
 
}