package com.alfresco.support.alfrescodb.controller;

import com.alfresco.support.alfrescodb.dao.ActivitiesFeedMapper;
import com.alfresco.support.alfrescodb.dao.DbSizeMapper;
import com.alfresco.support.alfrescodb.dao.NodeListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import java.util.List;

@ComponentScan
@Component
public class SqlMapperController {

    @Value("${spring.datasource.platform}")
    private String dbType;

    @Autowired
    private DbSizeMapper dbSizeMapper;

    @Autowired
    private NodeListMapper nodeListMapper;

    @Autowired
    private ActivitiesFeedMapper activitiesFeedMapper;

    public List findTablesInfo(){
        if (dbType.equalsIgnoreCase("postgres")){
            return dbSizeMapper.findTablesInfoPostgres();
        } else if (dbType.equalsIgnoreCase("mysql")){
            return dbSizeMapper.findTablesInfoMysql();
        }

        return null;
    }

    public String findDbSize(){
        if (dbType.equals("postgres")){
            return dbSizeMapper.findDbSizePostgres();
        } else if (dbType.equalsIgnoreCase("mysql")){
            return dbSizeMapper.findDbSizeMysql();
        }

        return null;
    }

    public List findNodesByStore(){
        if (dbType.equalsIgnoreCase("postgres")){
            return nodeListMapper.findNodesByStorePostgres();
        } else if (dbType.equalsIgnoreCase("mysql")){
            return nodeListMapper.findNodesByStoreMySQL();
        }

        return null;
    }

    public List findNodesByContentType(){
        if (dbType.equalsIgnoreCase("postgres")){
            return nodeListMapper.findNodesSizeByContentTypePostgres();
        } else if (dbType.equalsIgnoreCase("mysql")){
            return nodeListMapper.findNodesSizeByContentTypeMySQL();
        }

        return null;
    }

    public List findActivitiesByActivityType(){
        if (dbType.equalsIgnoreCase("postgres")){
            return activitiesFeedMapper.findActivitiesByActivityTypePostgres();
        } else if (dbType.equalsIgnoreCase("mysql")){
            return activitiesFeedMapper.findActivitiesByActivityTypeMySQL();
        }

        return null;
    }

    public List findActivitiesByUser(){
        if (dbType.equalsIgnoreCase("postgres")){
            return activitiesFeedMapper.findActivitiesByUserPostgres();
        } else if (dbType.equalsIgnoreCase("mysql")){
            return activitiesFeedMapper.findActivitiesByUserMySQL();
        }

        return null;
    }


    public List findActivitiesByApplicationInterface(){
        if (dbType.equalsIgnoreCase("postgres")){
            return activitiesFeedMapper.findActivitiesByApplicationInterfacePostgres();
        } else if (dbType.equalsIgnoreCase("mysql")){
            return activitiesFeedMapper.findActivitiesByApplicationInterfaceMySQL();
        }

        return null;
    }
}
