package com.medical.service;

import com.medical.websocket.PyClient;
import org.java_websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;


/**
 * @Author Alan_
 * @create 2020/10/10 20:05
 */
@Service
public class PyService {

    @Autowired
    private PyClient client;
    public String msg(String msg)  {

        synchronized (this){
            try {
                client.sendMsg(msg);
                return client.sendMsg(msg);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
