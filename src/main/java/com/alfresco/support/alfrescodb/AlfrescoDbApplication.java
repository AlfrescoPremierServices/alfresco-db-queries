package com.alfresco.support.alfrescodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath:application-context.xml")
@SpringBootApplication
public class AlfrescoDbApplication {
    private static final Logger log = LoggerFactory.getLogger(AlfrescoDbApplication.class);

    public static void main(String args[]) {
        SpringApplication.run(AlfrescoDbApplication.class, args);
    }
}