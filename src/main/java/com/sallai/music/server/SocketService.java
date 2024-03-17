package com.sallai.music.server;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.sallai.music.module.vo.QrUrlPojo;
import com.sallai.music.server.third.QrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SocketService
 * @Description TODO
 * @Author sallai
 * @Email sallai@aliyun.com
 * @Date 10:23 PM 3/15/2024
 * @Version 1.0
 **/
@Component
@Slf4j
public class SocketService implements CommandLineRunner {
    @Autowired
    private SocketIOServer socketIOServer;

    @Autowired
    private QrService qrService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public void run(String... args) throws Exception {
        socketIOServer.start();
    }
    public static ConcurrentMap<String, SocketIOClient> socketIOClientMap = new ConcurrentHashMap<>();

    /**
     * 客户端连接的时候触发
     *
     * @param client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String sessionId = client.getSessionId().toString();
        //存储SocketIOClient，用于发送消息
        socketIOClientMap.put(sessionId, client);
        //回发消息
        client.sendEvent("message", "onConnect back");
        log.info("客户端:" + client.getSessionId() + "已连接,id=" + sessionId);
    }

    /**
     * 客户端关闭连接时触发
     *
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        log.info("客户端:" + client.getSessionId() + "断开连接");
    }

    /**
     * 客户端事件
     *
     * @param client  　客户端信息
     * @param request 请求信息
     * @param data    　客户端发送数据
     */
    @OnEvent(value = "messageevent")
    public void onEvent(SocketIOClient client, AckRequest request, Object data) {
        log.info("发来消息：" + data);
        //回发消息
        client.sendEvent("messageevent", "我是服务器都安发送的信息");
        //广播消息
        sendBroadcast();
    }

    /**
     * 客户端事件
     *
     * @param client  　客户端信息
     * @param request 请求信息
     * @param data    　客户端发送数据
     */
    @OnEvent(value = "qrLogin")
    public void onQrLogin(SocketIOClient client, AckRequest request, Object data) throws IOException {
        log.info("扫码登录：" + client.getSessionId().toString());
        QrUrlPojo qrLoginUrl = qrService.getQrLoginUrl(QrUrlPojo.class);
        ValueOperations<String, String> stringValue = redisTemplate.opsForValue();
        stringValue.set(qrLoginUrl.getData().getTempUserId(),client.getSessionId().toString(),3, TimeUnit.MINUTES);
        client.sendEvent("qrUrl",qrLoginUrl);
    }

    /**
     * 根据sessionid 发送给客户端
     * @param sessionId
     * @param key
     * @param data
     */
    public static void sendToUser(String sessionId,String key,Object data) {
        SocketIOClient socketIOClient = socketIOClientMap.get(sessionId);
        if(null == socketIOClient) {
            throw new RuntimeException("no find the socket client");
        }
        socketIOClient.sendEvent(key,data);
    }

    /**
     * 广播消息
     */
    public void sendBroadcast() {
        for (SocketIOClient client : socketIOClientMap.values()) {
            if (client.isChannelOpen()) {
                client.sendEvent("Broadcast", "当前时间", System.currentTimeMillis());
            }
        }
    }
}
