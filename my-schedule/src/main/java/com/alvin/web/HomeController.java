package com.alvin.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/4/17.
 */
@RestController
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "Welcome";
    }
}
