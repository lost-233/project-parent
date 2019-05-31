package com.zhaoming.http.config;

import com.zhaoming.http.HttpService;
import com.zhaoming.http.impl.RestTemplateHttpServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * http配置
 *
 * @author hhx
 */
@Configuration
public class HttpConfig {

    /**
     * 默认使用RestTemplate
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public HttpService httpService(){
        return new RestTemplateHttpServiceImpl();
    }

}
