package com.nuls.io.config.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;

import com.nuls.io.security.AjaxAuthenticationFailureHandler;
import com.nuls.io.security.AjaxAuthenticationProvider;
import com.nuls.io.security.AjaxAuthenticationSuccessHandler;
import com.nuls.io.security.MyAccessDecisionManager;
import com.nuls.io.security.MySecurityFilter;
import com.nuls.io.security.MySecurityMetadataSource;
import com.nuls.io.security.MyTokenBasedRememberMeServices;
import com.nuls.io.service.SpringSecurityService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
/**
 * 
 *
 * @author hhu
 * @version $Id: WebSecurityConfig.java, v 0.1 2017年5月16日 下午1:34:20 hhu Exp $
 */
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${csrf.execludeUrls}")
    private String                           execludeUrls;
    @Value("${spring.security.remember.key}")
    private String                           remKey;
    @Value("${spring.security.remember.parameter}")
    private String                           remParam;
    @Value("${spring.security.remember.cookieName}")
    private String                           remCookieName;

    @Autowired
    private UserDetailsService               userDetailsService;

    @Autowired
    private MyAccessDecisionManager          myAccessDecisionManager;
    @Autowired
    private MySecurityMetadataSource         mySecurityMetadataSource;
    @Autowired
    private AjaxAuthenticationFailureHandler ajaxFailureHandler;
    @Autowired
    private AjaxAuthenticationSuccessHandler ajaxSuccessHandler;
    @Autowired
    private SpringSecurityService            springSecurityService;

    public MyTokenBasedRememberMeServices rememberMeServices() {
        MyTokenBasedRememberMeServices service = new MyTokenBasedRememberMeServices(remKey,
            userDetailsService);
        service.setCookieName(remCookieName);
        service.setParameter(remParam);
        service.setSpringSecurityService(springSecurityService);
        return service;
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    public MySecurityFilter mySecurityFilter() throws Exception {
        MySecurityFilter filter = new MySecurityFilter();
        filter.setAuthenticationManager(authenticationManager());
        filter.setAccessDecisionManager(myAccessDecisionManager);
        filter.setSecurityMetadataSource(mySecurityMetadataSource);
        return filter;
    }

    public AjaxAuthenticationProvider ajaxLoginFilter() throws Exception {
        AjaxAuthenticationProvider provider = new AjaxAuthenticationProvider(springSecurityService);
        provider.setAuthenticationManager(authenticationManager());
        provider.setAuthenticationFailureHandler(ajaxFailureHandler);
        provider.setAuthenticationSuccessHandler(ajaxSuccessHandler);
        provider.setRememberMeServices(rememberMeServices());
        return provider;
    }

    public UserRequiresCsrfMatcher userRequiresCsrfMatcher() {
        List<String> urls = new ArrayList<String>();
        if (!StringUtils.isEmpty(execludeUrls)) {
            urls = Arrays.asList(StringUtils.split(execludeUrls, ","));
        }
        UserRequiresCsrfMatcher matcher = new UserRequiresCsrfMatcher();
        matcher.setExecludeUrls(urls);
        return matcher;
    }

    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        List<SessionAuthenticationStrategy> delegateStrategies = new ArrayList<SessionAuthenticationStrategy>();
        ConcurrentSessionControlAuthenticationStrategy astat = new ConcurrentSessionControlAuthenticationStrategy(
            sessionRegistry());
        astat.setMaximumSessions(1);
        astat.setExceptionIfMaximumExceeded(false);

        delegateStrategies.add(astat);
        delegateStrategies.add(new SessionFixationProtectionStrategy());
        delegateStrategies.add(new RegisterSessionAuthenticationStrategy(sessionRegistry()));

        CompositeSessionAuthenticationStrategy sas = new CompositeSessionAuthenticationStrategy(
            delegateStrategies);

        return sas;
    }

    //@Bean security session监听器，可在创建和销毁session时触发相应方法
    //    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() { //(5)
    //        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(
    //            new HttpSessionEventPublisher());
    //    }

    /**
     * 1、
     * web相关配置静态
     * @param web
     * @throws Exception
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.WebSecurity)
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html", "/doc.html", "/swagger-ui.html", "/webjars/**",
            "/swagger-resources/**", "/v2/**", "/api", "/db", "/favicon.ico", "/resources/**",
            "/druid/**", "/message/*");
    }

    /**
     * 2、
     * @param http
     * @throws Exception
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 解决跨域
         * 1、csrf 禁用，ajax跨域请求会被拦截判定失效  csrf.disable()
         * 2、注册用户自定义的请求匹配器requireCsrfProtectionMatcher
         */
        http.exceptionHandling().accessDeniedPage("/authExp").and().authorizeRequests()
            .antMatchers("/message/*").permitAll().antMatchers("/file/method=download*")
            .permitAll().antMatchers("/error").permitAll().antMatchers("/**").hasRole("USER").and()
            .formLogin().loginPage("/logon").permitAll().and().logout().invalidateHttpSession(true)
            .logoutUrl("/logout").and().csrf()
            .requireCsrfProtectionMatcher(userRequiresCsrfMatcher()).and().rememberMe().key(remKey)
            .rememberMeServices(rememberMeServices()).and().sessionManagement()
            .sessionAuthenticationStrategy(sessionAuthenticationStrategy())
            .invalidSessionUrl("/logon").and()
            .addFilterAt(ajaxLoginFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterAt(mySecurityFilter(), FilterSecurityInterceptor.class);
    }

    /**
     * 3、
     * @param auth
     * @throws Exception
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(userDetailsService);
    }
}