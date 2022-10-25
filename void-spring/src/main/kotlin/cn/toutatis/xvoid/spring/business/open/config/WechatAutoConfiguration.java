package cn.toutatis.xvoid.spring.business.open.config;

import cn.toutatis.xvoid.spring.business.open.request.WechatOfficialAccountRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Toutatis_Gc
 * @date 2022/10/25 12:42
 */
@Configuration
@EnableConfigurationProperties(WechatConfiguration.class)
public class WechatAutoConfiguration {

    private final WechatConfiguration wechatConfiguration;

    public WechatAutoConfiguration(WechatConfiguration wechatConfiguration) {
        this.wechatConfiguration = wechatConfiguration;
    }

    @Bean
    public WechatOfficialAccountRequest wechatOfficialAccountRequest(){
        WechatOfficialAccountRequest wechatOfficialAccountRequest = new WechatOfficialAccountRequest(
                wechatConfiguration.getOfficialAccountConfig().getAppId(),
                wechatConfiguration.getOfficialAccountConfig().getAppSecret());
        wechatOfficialAccountRequest.setToken(wechatConfiguration.getOfficialAccountConfig().getToken());
        return wechatOfficialAccountRequest;
    }

}
