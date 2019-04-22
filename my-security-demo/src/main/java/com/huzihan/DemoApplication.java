package com.huzihan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication(scanBasePackages = "com.huzihan.*")
public class DemoApplication {

    public static void main(String[] args){
        SpringApplication.run(DemoApplication.class,args);
    }

    @GetMapping("/hello")
    public String helloWorld(){
        return "hello security ";
    }

}
