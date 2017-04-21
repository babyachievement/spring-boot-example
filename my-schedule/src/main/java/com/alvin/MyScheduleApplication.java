package com.alvin;

import org.springframework.boot.ImageBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class MyScheduleApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MyScheduleApplication.class);
        app.setBanner(new ImageBanner(new ClassPathResource("banner.jpg")));
        app.run(args);
    }
}
