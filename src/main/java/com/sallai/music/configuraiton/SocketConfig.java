package com.sallai.music.configuraiton;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName SocketConfig
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 10:25 PM 3/15/2024
 * @Version 1.0
 **/
@Configuration
public class SocketConfig {
        /**
         * netty-socketio服务器
         */
        @Bean
        public SocketIOServer socketIOServer() {
            com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
//            config.setHostname("localhost");
            config.setPort(3015);
            //解决对此重启服务时，netty端口被占用问题
            com.corundumstudio.socketio.SocketConfig tmpConfig = new com.corundumstudio.socketio.SocketConfig();
            tmpConfig.setReuseAddress(true);
            config.setSocketConfig(tmpConfig);
            SocketIOServer server = new SocketIOServer(config);
            return server;
        }

        /**
         * 用于扫描netty-socketio的注解，比如 @OnConnect、@OnEvent
         */
        @Bean
        public SpringAnnotationScanner springAnnotationScanner() {
            return new SpringAnnotationScanner(socketIOServer());
        }
}
