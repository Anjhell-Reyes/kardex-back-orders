package com.kardex.infrastructure.out.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Value("${fixed.token}")
    private String FIXED_TOKEN;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (!requestTemplate.headers().containsKey("Authorization")) {
            requestTemplate.header("Authorization", "Bearer " + FIXED_TOKEN);
        }
    }
}
