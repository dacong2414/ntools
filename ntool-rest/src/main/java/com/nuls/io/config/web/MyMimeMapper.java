package com.nuls.io.config.web;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.context.annotation.Configuration;

@Configuration
/**
 * 定义web mime mapper
 *
 * @author hhu
 * @version $Id: MyMimeMapper.java, v 0.1 2017年5月17日 下午3:19:59 hhu Exp $
 */
public class MyMimeMapper implements EmbeddedServletContainerCustomizer {
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        mappings.add("docx", "application/msword; charset=utf-8");
        container.setMimeMappings(mappings);
    }
}