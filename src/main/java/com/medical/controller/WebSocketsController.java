package com.medical.controller;

import com.medical.entity.Result;
import com.medical.websocket.CustomizedWebSocketClient;
import com.medical.websocket.WebSocketClientSyncCallback;
import org.java_websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
@RequestMapping(value = "/socket")
public class WebSocketsController {


    @Autowired
    private CustomizedWebSocketClient socketClient;

    @ResponseBody
    @PostMapping(value = "/clientCallback")
    public Result testClientCallback(HttpServletRequest request, @RequestParam String msg)  {
        System.out.println("请求msg" + msg);
        final String[] callbackMessage = {null};
        try {
            if(socketClient.isClosed()){
                if (socketClient.getReadyState().equals(WebSocket.READYSTATE.NOT_YET_CONNECTED)) {
                    try {
                        socketClient.connect();
                    } catch (IllegalStateException e) {
                        callbackMessage[0] = "服务器正忙";
                    }
                } else if (socketClient.getReadyState().equals(WebSocket.READYSTATE.CLOSING) || socketClient.getReadyState().equals(WebSocket.READYSTATE.CLOSED)) {
                    socketClient.reconnect();
                }
                while(!socketClient.getReadyState().equals(WebSocket.READYSTATE.OPEN)){
                    //System.out.println("还没有打开");
                }
            }
            socketClient.send(msg,new WebSocketClientSyncCallback(){
                @Override
                public void callback(String message) {
                    callbackMessage[0] = message;
                }
            });
        } catch (Exception e) {
            callbackMessage[0] = "服务器正忙";
            e.printStackTrace();
        }

        System.out.println("controller" + callbackMessage[0]);
        if(callbackMessage[0] == null){
            callbackMessage[0] = "网络正忙";
        }
        return Result.ok().data("msg",callbackMessage[0]);
    }
    //3.添加定时任务
    /*@Scheduled(cron = "0/45 * * * * ?")
    //或直接指定时间间隔，例如：30秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        if(!socketClient.isClosed()){
            socketClient.close();
        }
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }*/
}
