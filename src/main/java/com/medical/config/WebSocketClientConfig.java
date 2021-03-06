package com.medical.config;

import com.medical.websocket.CustomizedWebSocketClient;
import org.java_websocket.WebSocket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * WebSocket客户端配置类
 */
@Configuration
public class WebSocketClientConfig {


    /**
     * socket连接地址
     */
    @Value("${com.dl.socket.url}")
    private String webSocketUri;

    /**
     * 注入Socket客户端
     * @return
     */
    @Bean
    public CustomizedWebSocketClient initWebSocketClient(){
        URI uri = null;
        try {
            uri = new URI(webSocketUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        CustomizedWebSocketClient webSocketClient = new CustomizedWebSocketClient(uri);
        //启动时创建客户端连接
         webSocketClient.connect();
        while(!webSocketClient.getReadyState().equals(WebSocket.READYSTATE.OPEN)){
            //System.out.println("还没有打开");
        }
        return webSocketClient;
    }

}
