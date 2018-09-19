package com.nuls.io.config.swagger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {
    @Value("${swagger.basePackage}")
    private String basePackage;
    @Value("${swagger.title}")
    private String title;
    @Value("${swagger.description}")
    private String description;
    @Value("${swagger.termsOfServiceUrl}")
    private String termsOfServiceUrl;
    @Value("${swagger.version}")
    private String version;
    @Value("${swagger.license}")
    private String license;
    @Value("${swagger.licenseUrl}")
    private String licenseUrl;
    @Value("${swagger.contact.name}")
    private String contactName;
    @Value("${swagger.contact.url}")
    private String contactUrl;
    @Value("${swagger.contact.mail}")
    private String contactMail;

    @Bean
    public Docket createRestApi() {
        //List<ResponseMessage> list = new ArrayList<>();
        //list.add(new ResponseMessageBuilder().code(500).message("系统错误，请联系管理员!")
        //    .responseModel(new ModelRef("Error")).build());
        /*ParameterBuilder tokenPar = new ParameterBuilder();
        ParameterBuilder accessKeySecret = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("x-access-token").description("令牌").modelRef(new ModelRef("string"))
            .parameterType("header").required(false).build();
        accessKeySecret.name("AccessKeySecret").description("签名所需的秘钥")
            .modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        pars.add(accessKeySecret.build());*/
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
            .apis(RequestHandlerSelectors.basePackage(basePackage)).paths(PathSelectors.any())
            .build().useDefaultResponseMessages(false).securitySchemes(newArrayList());
        //.globalResponseMessage(RequestMethod.GET, list);
        //.globalResponseMessage(requestMethod, responseMessages);
    }

    /**
     *
     * @param apiKey
     * @return
     */
    private List<? extends SecurityScheme> newArrayList() {
        ApiKey apiKey1 = new ApiKey("Authorization", "value", "header");
        ApiKey apiKey2 = new ApiKey("MsgIdent", "value", "header");
        ApiKey apiKey3 = new ApiKey("SignDate", "value", "header");
        List<ApiKey> list = new ArrayList<ApiKey>();
        list.add(apiKey1);
        list.add(apiKey2);
        list.add(apiKey3);
        return list;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(title).description(description)
            .termsOfServiceUrl(termsOfServiceUrl).version(version).license(license)
            .contact(new Contact(contactName, contactUrl, contactMail)).licenseUrl(licenseUrl)
            .build();
    }

    @Bean
    public UiConfiguration getUiConfig() {
        return new UiConfiguration(null,// url,暂不用
            "list", // docExpansion          => none | list
            "alpha", // apiSorter             => alpha
            "model", // defaultModelRendering => schema | model
            UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, // enableJsonEditor      => true | false
            true, null); // showRequestHeaders    => true | false
    }
    //注解使用注意事项
    //http://www.cnblogs.com/java-zhao/p/5348113.html
}