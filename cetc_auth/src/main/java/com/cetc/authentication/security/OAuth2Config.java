package com.cetc.authentication.security;

import com.cetc.authentication.service.impl.RandomAuthenticationKeyGenerator;
import com.cetc.model.admin.LoginSysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.HashMap;
import java.util.Map;

/*
* 定义哪些应用程序可以使用服务,对spring的AuthorizationServerConfigurerAdapter类进行了扩展
* 继承AuthorizationServerConfigurerAdapter，配置OAuth2认证服务器，需要配置授权和Token相关的三个配置。
*AuthorizationServerSecurityConfigurer：声明安全约束，允许那些请求可以访问和禁止访问。
*ClientDetailsServiceConfigurer：配置独立的client客户端信息，包括授权范围、授权方式、客户端权限等。
*授权方式包括password、implicit、client_redentials、authorization_code四种方式，其中密码授权方式必须结合 AuthenticationManager 进行配置。
*AuthorizationServerEndpointsConfigurer：配置授权服务器的Token存储方式、Token配置、授权模式

* */
@Configuration
@EnableAuthorizationServer //用于告诉springcloud，该服务将作为oAuth2服务
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
    * 覆盖configure()方法。这定义了哪些客户端将注册到服务
    *
    * */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("cetc")
                .secret(passwordEncoder.encode("cetc"))
                .authorizedGrantTypes("refresh_token","password","client_credentials")
                .scopes("webclient")
                .accessTokenValiditySeconds(600)
                .refreshTokenValiditySeconds(60000);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }

    @Bean
   public TokenStore tokenStore(){
       RedisTokenStore redisTokenStore = new MyRedisTokenStoreService(redisConnectionFactory,clientDetailsService);
        // 2018.08.04添加,解决同一username每次登陆access_token都相同的问题
        redisTokenStore.setAuthenticationKeyGenerator(new RandomAuthenticationKeyGenerator());
        return redisTokenStore;
   }
    /*
    * 该方法定义了AuthenticationServerConfigurer中使用的不同组件。
    * 这段代码告诉Spring使用Spring提供的默认验证管理器和用户详细信息服务
    *InMemoryTokenStore 默认方式，保存在本地内存
    *JdbcTokenStore 存储数据库
    *RedisTokenStore 存储Redis，这应该是微服务下比较常用方式
    *JwtTokenStore 分布式跨域JWT存储方式
    * */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(this.authenticationManager);
        endpoints.tokenStore(tokenStore());
        endpoints.userDetailsService(userDetailsService);

        //授权码模式下的redis存储
        // 2018.07.13 将当前用户信息追加到登陆后返回数据里
            endpoints.tokenEnhancer((accessToken, authentication) -> {
                addLoginUserInfo(accessToken, authentication);
                return accessToken;
            });
        }

    private void addLoginUserInfo(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        if (accessToken instanceof DefaultOAuth2AccessToken) {
            DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;

            Authentication userAuthentication = authentication.getUserAuthentication();
            Object principal = userAuthentication.getPrincipal();
            if (principal instanceof LoginSysUser) {
                LoginSysUser loginUser = (LoginSysUser) principal;

                Map<String, Object> map = new HashMap<>(defaultOAuth2AccessToken.getAdditionalInformation()); // 旧的附加参数
                map.put("loginUser", loginUser); // 追加当前登陆用户

                defaultOAuth2AccessToken.setAdditionalInformation(map);
            }
        }
    }



}
