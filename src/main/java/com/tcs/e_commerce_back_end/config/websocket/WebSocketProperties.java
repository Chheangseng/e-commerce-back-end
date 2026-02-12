package com.tcs.e_commerce_back_end.config.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ws-config-properties")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketProperties {
    private String pathWS;
    private String pathWsClient;
    private String pathWsPush;
}
