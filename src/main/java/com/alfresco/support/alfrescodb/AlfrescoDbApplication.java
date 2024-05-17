package com.alfresco.support.alfrescodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:application-context.xml")
public class AlfrescoDbApplication {
    public static void main(String args[]) {
        SpringApplication.run(AlfrescoDbApplication.class, args);
    }
}