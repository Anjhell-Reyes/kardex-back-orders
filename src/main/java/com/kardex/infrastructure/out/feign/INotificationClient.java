package com.kardex.infrastructure.out.feign;

import com.kardex.infrastructure.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notifications-service", url = "http://localhost:8083", configuration = FeignConfiguration.class)
public interface INotificationClient {

    @PostMapping("/notifications/orderUpdate")
    void saveOrderNotification(@RequestParam String userId, @RequestParam String productName,@RequestParam String orderStatus);
}
