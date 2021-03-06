package com.alfresco.support.alfrescodb;

import com.alfresco.support.alfrescodb.controllers.WebController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.ui.Model;

@SpringBootApplication
@ImportResource("classpath:application-context.xml")
public class AlfrescoDbApplication {
    private static final Logger log = LoggerFactory.getLogger(AlfrescoDbApplication.class);

    public static void main(String args[]) {
        SpringApplication.run(AlfrescoDbApplication.class, args);
    }
}