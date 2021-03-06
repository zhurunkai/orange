package org.zust.interfaceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class InterfaceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterfaceApiApplication.class, args);
    }

}
