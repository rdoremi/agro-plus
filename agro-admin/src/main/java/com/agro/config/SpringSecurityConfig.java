package com.agro.config;

import com.agro.service.impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//@Configuration
//@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /*  @Bean
      public PasswordEncoder passwordEncoder(){
          return new BCryptPasswordEncoder();
      }*/
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().sameOrigin();

        http.authorizeRequests()
                .antMatchers("/assets","/layui/**", "/static/**","/webSocket/**","/kaptcha/**", "/xadmin/**", "/login","/js/**", "/fore/**", "/webrtc/**")
                .permitAll()
                .antMatchers( "/sys/**").authenticated()
                .anyRequest().permitAll();
        http.formLogin().loginPage("/login").loginProcessingUrl("/login")
                .successHandler(new AuthenticationSuccessHandler() {


                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

                        //httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
                        //httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
                        httpServletResponse.setContentType("application/json;charset=UTF-8");
                        httpServletResponse.setStatus(HttpStatus.OK.value());
                        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(authentication));
                    }
                }).failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

                httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
                httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
                //httpServletResponse.getWriter().write(objectMapper.writeValueAsString(ServerResponse.createByErrorMessage("登录失败")));
                Map<String, Object> map = new HashMap<>();
                if ("json".equals("json")) {
                    //httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
                    //httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
                    //httpServletResponse.setContentType("application/json;charset=UTF-8");
                    //httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

                    /*UsernameNotFoundException（用户不存在）
                        DisabledException（用户已被禁用）
                        BadCredentialsException（坏的凭据）
                        LockedException（账户锁定）
                        AccountExpiredException （账户过期）
                        CredentialsExpiredException（证书过期）*/

                    if (e instanceof LockedException) {
                        //System.out.println("锁定了");
                        map.put("message", "账号异常,登录失败!");
                    } else if (e instanceof BadCredentialsException) {/*BadCredentialsException*/
                        map.put("message", "账号或密码错误,登录失败!");
                    } else if (e instanceof DisabledException) {
                        map.put("message", "账号被禁用,登录失败!");
                    } else if (e instanceof AccountExpiredException) {
                        map.put("message", "账号已过期,登录失败!");
                    } else {
                        map.put("message", "登录失败!账号异常");
                    }
                    /*else if (e instanceof AuthenticationException){
                        map.put("message", "用户不存在");
                    }*/
                }
                System.out.println(map.get("message"));
                httpServletResponse.getWriter().write(objectMapper.writeValueAsString(map.get("message")));


            }
        }).and()
                .logout().permitAll().invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.sendRedirect("/login");
                    }
                });
        http.csrf().disable();
//        http.cors();
        //super.configure(http);
        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                httpServletResponse.setStatus(httpServletResponse.SC_FORBIDDEN);
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.sendRedirect("/403");
            }
        })

        ;
    }
}
