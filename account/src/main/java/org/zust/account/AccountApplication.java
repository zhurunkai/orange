package org.zust.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccountApplication {

    public static void main(String[] args) {
        System.out.println(123);
        SpringApplication.run(AccountApplication.class, args);
    }

}
