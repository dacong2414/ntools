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



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${csrf.execludeUrls}")
    private String                           execludeUrls;
    @Value("${spring.security.remember.key}")
    private String                           remKey;
    @Value("${spring.security.remember.parameter}")
    private String                           remParam;
    @Value("${spring.security.remember.cookieName}")
    private String                           remCookieName;

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
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
            "/swagger-resources/**", "/v2/**", "/api", "/db", "/favicon.ico","/user/*");
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
         .antMatchers("/user/*").permitAll().antMatchers("/file/method=download*")
         .permitAll().antMatchers("/error").permitAll().antMatchers("/**").hasRole("USER").and()
         .formLogin().loginPage("/logon").permitAll().and().logout().invalidateHttpSession(true)
         .logoutUrl("/logout").and().csrf()
         .requireCsrfProtectionMatcher(userRequiresCsrfMatcher()).and().rememberMe().key(remKey)
         .and().sessionManagement()
         .sessionAuthenticationStrategy(sessionAuthenticationStrategy())
         .invalidSessionUrl("/logon");
    }

}