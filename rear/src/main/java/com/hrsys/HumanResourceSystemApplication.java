package com.hrsys;

import com.hrsys.mapper.SSItemsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HumanResourceSystemApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(HumanResourceSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//        设置一个全局变量

    }
}
