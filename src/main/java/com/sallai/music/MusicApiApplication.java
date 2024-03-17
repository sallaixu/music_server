package com.sallai.music;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sallai.music.dao.mapper")
public class MusicApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicApiApplication.class, args);
    }

}
