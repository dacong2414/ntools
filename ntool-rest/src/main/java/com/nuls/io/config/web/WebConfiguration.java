/**
 * Ambition Inc.
 * Copyright (c) 2006-2017 All Rights Reserved.
 */
package com.nuls.io.config.web;

import java.util.List;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

   
    /**
     * 默认使用fastjson处理返回对象转化
     * @param converters
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#configureMessageConverters(java.util.List)
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);

        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastConverter.setFastJsonConfig(fastJsonConfig);

        converters.add(fastConverter);
    }

    /**
     * 配置session，request监听器，在service,controller中可以直接通过
     * @autoware HttpSession session;@autoware HttpServletRequest request;方式直接获取
     *
     * @return
     */
    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    /**  
     * 文件上传配置  
     * @return  
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大  
        factory.setMaxFileSize("500MB"); //KB,MB  
        /// 设置总上传数据总大小  
        factory.setMaxRequestSize("500MB");
        return factory.createMultipartConfig();
    }
}
