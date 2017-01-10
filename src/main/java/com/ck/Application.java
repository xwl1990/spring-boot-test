package com.ck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) {
        LOG.info("start...");
        SpringApplication.run(Application.class, args);
        System.out.println("Home:"+System.getProperty("user.home"));
        System.out.println("JDK Versoin:"+System.getProperty("java.version"));
        System.out.println("OS:"+System.getProperty("os.name"));
        System.out.println("SpringVersion:"+SpringVersion.getVersion());
        System.out.println("-----------------------------------");
        System.out.println("|                                 |");
        System.out.println("|       Startup complete!         |");
        System.out.println("|                                 |");
        System.out.println("-----------------------------------");
    }
}

	