package com.metacoding.refsocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// SRP: 마트 점원(메세지 브로커) 세팅
@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // sub(브라우저): /바나나
    // pub(브라우저): /바나나

    // 웹소켓 연결 엔드포인트 설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/connect")
                .setAllowedOriginPatterns("*");
    }

    // 구독, 발행 엔드포인트 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub"); // sub로 시작하는 모든주소 >> 구독주소 >> /sub로 시작되는 주소가 호출되면 이메소드가 실행됨
        registry.setApplicationDestinationPrefixes("/pub"); // pub로 시작하는 모든주소 >> 발행주소
        // pub로 발행하면 sub로 다 날아감
    }
}
