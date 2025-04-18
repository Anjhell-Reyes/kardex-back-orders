package com.kardex.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kardex.domain.api.ICartServicePort;
import com.kardex.domain.api.IEmailServicePort;
import com.kardex.domain.api.IOrderServicePort;
import com.kardex.domain.spi.*;
import com.kardex.domain.usecase.CartUseCase;
import com.kardex.domain.usecase.EmailUseCase;
import com.kardex.domain.usecase.OrderUseCase;
import com.kardex.infrastructure.out.adapter.*;
import com.kardex.infrastructure.out.feign.INotificationClient;
import com.kardex.infrastructure.out.feign.IProductClient;
import com.kardex.infrastructure.out.mapper.CartEntityMapper;
import com.kardex.infrastructure.out.mapper.OrderEntityMapper;
import com.kardex.infrastructure.out.mapper.OrderStatusHistoryEntityMapper;
import com.kardex.infrastructure.out.mapper.StatusEntityMapper;
import com.kardex.infrastructure.out.repository.ICartRepository;
import com.kardex.infrastructure.out.repository.IOrderRepository;
import com.kardex.infrastructure.out.repository.IOrderStatusHistoryRepository;
import com.kardex.infrastructure.out.repository.IStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IOrderRepository orderRepository;
    private final ICartRepository cartRepository;
    private final IProductClient productClient;
    private final INotificationClient notificationClient;
    private final OrderEntityMapper orderEntityMapper;
    private final CartEntityMapper cartEntityMapper;
    private final IStatusRepository statusRepository;
    private final IOrderStatusHistoryRepository orderStatusHistoryRepository;
    private final OrderStatusHistoryEntityMapper orderStatusHistoryEntityMapper;
    private final StatusEntityMapper statusEntityMapper;
    private final JavaMailSender mailSender;
    private final RestTemplateBuilder restTemplateBuilder;
    private final ObjectMapper objectMapper;

    @Bean
    public IOrderPersistencePort orderPersistencePort(){
        return new OrderJpaAdapter(orderRepository, orderEntityMapper);
    }

    @Bean
    public IPaypalClientPersistencePort paypalClientPersistencePort(){
        return new PayPalClient(restTemplateBuilder.build(), objectMapper);
    }

    @Bean
    public IStatusPersistencePort statusPersistencePort(){
        return new StatusJpaAdapter(statusRepository, statusEntityMapper);
    }

    @Bean
    public IOrderStatusHistoryPersistencePort orderStatusHistoryPersistencePort() {
        return new OrderStatusHistoryJpaAdapter(orderStatusHistoryRepository, orderStatusHistoryEntityMapper);
    }

    @Bean
    public IProductPersistencePort productClientPersistencePort() {
        return new ProductServiceAdapter(productClient);
    }

    @Bean
    public INotificationPersistencePort notificationPersistencePort(){
        return new NotificationServiceAdapter(notificationClient);
    }

    @Bean
    public ICartPersistencePort cartPersistencePort(){
        return new CartJpaAdapter(cartRepository, cartEntityMapper);
    }

    @Bean
    public IEmailPersistencePort emailPersistencePort() {
        return new EmailServiceAdapter(mailSender);
    }

    @Bean
    public IOrderServicePort productServicePort() {
        return new OrderUseCase(orderPersistencePort(),statusPersistencePort(), productClientPersistencePort(), notificationPersistencePort(), orderStatusHistoryPersistencePort(),cartServicePort(),cartPersistencePort(),emailServicePort());
    }

    @Bean
    public IEmailServicePort emailServicePort() {
        return new EmailUseCase(emailPersistencePort());
    }

    @Bean
    public ICartServicePort cartServicePort() {
        return new CartUseCase(cartPersistencePort(), productClientPersistencePort());
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
