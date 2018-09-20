package com.nuls.io;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Spring boot 启动类
 * @EnableAsync 启动异步配置 （ThreadPoolBean）
 * 
 * @author hhu
 * @version $Id: Application.java, v 0.1 2017年6月22日 下午3:20:04 hhu Exp $
 */

@SpringBootApplication
@EnableAsync
@MapperScan(basePackages = "com.nuls.io.dao")
public class Application extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}