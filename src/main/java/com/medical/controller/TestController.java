package com.medical.controller;

import com.medical.service.PyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Alan_
 * @create 2020/10/10 20:08
 */
@RestController
public class TestController {

    @Autowired
    private PyService pyService;

    @GetMapping("/test")
    public String msg(String msg){

        String m= pyService.msg(msg);
        String mm= pyService.msg(msg);

        return mm;
    }
}
