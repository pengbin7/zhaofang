package com.pb.config;

import com.pb.security.AuthProvider;
import com.pb.security.LoginAuthFailHandler;
import com.pb.security.LoginUrlEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

/**
 * @Author pengbin
 * @date 2019/4/17 15:16
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)//开启@Secured 注解过滤权限
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Http权限控制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/login").permitAll()//管理员登录入口
                .antMatchers("/static/**").permitAll()//静态资源
                .antMatchers("/user/login").permitAll()//用户登录入口
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/api/user/**").hasAnyRole("ADMIN","USER")
                .and()
                .formLogin()
                .loginProcessingUrl("/login")//配置角色登录处理入口
                .failureHandler(authFailHandler())//登录验证失败处理
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logout/page")
                .deleteCookies("JESSIONID")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(urlEntryPoint())
                .accessDeniedPage("/403");

        http.csrf().disable();//禁用csrf，跨站请求伪造，一种防御策略
        http.headers().frameOptions().sameOrigin();

    }

    /**
     * 自定义认证策略
     * spring security 5中新增了多种加密方式，也改变了密码的格式
     */
    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
       /* auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("admin")
                .password(new BCryptPasswordEncoder().encode("123admin")).roles("ADMIN").and();
    */
       auth.authenticationProvider(authProvider()).eraseCredentials(true);

    }

    @Bean
    public AuthProvider authProvider(){
        return new AuthProvider();
    }

    @Bean
    public LoginUrlEntryPoint urlEntryPoint() {
        return new LoginUrlEntryPoint("/user/login");
    }

    @Bean
    public LoginAuthFailHandler authFailHandler() {
        return new LoginAuthFailHandler(urlEntryPoint());
    }

}
