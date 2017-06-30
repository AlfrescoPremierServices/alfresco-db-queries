package com.alfresco.support.alfrescodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
public class AlfrescoDbApplication {
    private static final Logger log = LoggerFactory.getLogger(AlfrescoDbApplication.class);

    public static void main(String args[]) {
        SpringApplication.run(AlfrescoDbApplication.class, args);
    }
}