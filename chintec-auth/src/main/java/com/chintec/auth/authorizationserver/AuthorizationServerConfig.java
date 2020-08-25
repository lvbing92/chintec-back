package com.chintec.auth.authorizationserver;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author rubin
 */
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * accessToken有效期
     */
    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 7200;
    private static final int REFRESH_TOKEN_VALIDITY_SECONDS = 7200;

    // 添加商户信息
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //方式一授权码授权模式    appid                           appkey
//        clients.inMemory().withClient("client_1").secret(passwordEncoder().encode("123456"))
//        .redirectUris("http://www.baidu.com").authorizedGrantTypes("authorization_code").scopes("all"). //回调地址 授权模式
//         accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)  //有效期时间
//         .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS); //刷新时间

        //方式二密码授权模式
        // withClient appid                                 appkey （也叫appSecrete）
        clients.inMemory().withClient("client_1").secret(passwordEncoder().encode("123456"))
                .authorizedGrantTypes("password", "client_credentials", "refresh_token")
                .scopes("all").accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS);
    }

    /**
     * 设置token类型
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager()).allowedTokenEndpointRequestMethods(HttpMethod.GET,
                HttpMethod.POST);
        //重新设置userDetailsService  防止刷新accessToken时候会报错！！！
        endpoints.authenticationManager(authenticationManager());
        endpoints.userDetailsService(userDetailsService());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        // 允许表单认证
        oauthServer.allowFormAuthenticationForClients();
        //允许check_token访问
        oauthServer.checkTokenAccess("permitAll()");
    }

    @Bean
    AuthenticationManager authenticationManager() {
        return new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return dabAuthenticationProvider().authenticate(authentication);
            }
        };
    }

    @Bean
    public AuthenticationProvider dabAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * 设置添加用户信息,从数据库中读取
     */
    @Bean
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
        userDetailsService.createUser(User.withUsername("user_1").password(passwordEncoder().encode("123456"))
                .authorities("ROLE_USER").build());
        userDetailsService.createUser(User.withUsername("user_2").password(passwordEncoder().encode("1234567"))
                .authorities("ROLE_USER").build());
        return userDetailsService;
    }

    /**
     * password加密的方式 相当于把PasswordEncoder类对象 注册到容器中
     *
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        // 加密方式
        return new BCryptPasswordEncoder();

    }
}
