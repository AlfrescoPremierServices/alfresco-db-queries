package com.alfresco.support.alfrescodb.controller;

import com.alfresco.support.alfrescodb.dao.ActivitiesFeedMapper;
import com.alfresco.support.alfrescodb.dao.DbSizeMapper;
import com.alfresco.support.alfrescodb.dao.LargeFolderMapper;
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

    @Value("${largeFolderSize}")
    private Integer largeFolderSize;

    @Autowired
    private DbSizeMapper dbSizeMapper;

    @Autowired
    private NodeListMapper nodeListMapper;

    @Autowired
    private ActivitiesFeedMapper activitiesFeedMapper;

    @Autowired
    private LargeFolderMapper largeFolderMapper;

    public List findTablesInfo(){
        if (dbType.equalsIgnoreCase("postgres")){
            return dbSizeMapper.findTablesInfoPostgres();
        } else if (dbType.equalsIgnoreCase("mysql")){
            return dbSizeMapper.findTablesInfoMysql();
        } else if (dbType.equalsIgnoreCase("microsoft")){
            return dbSizeMapper.findTablesInfoMSSql();
        } else if (dbType.equalsIgnoreCase("oracle")){
            return dbSizeMapper.findTablesInfoOracle();
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

    public List findNodesSize(){
        if (dbType.equalsIgnoreCase("postgres")){
            return nodeListMapper.findNodesSize();
        } else if (dbType.equalsIgnoreCase("mysql")){
            return nodeListMapper.findNodesSize();
        } else if (dbType.equalsIgnoreCase("oracle")){
            return nodeListMapper.findNodesSizeOracle();
        } else if (dbType.equalsIgnoreCase("microsoft")){
            return nodeListMapper.findNodesSizeMSSql();
        }

        return null;
    }

    public List findNodesSizeByMimeType(){
        return nodeListMapper.findNodesSizeByMimeType();
    }

    public List findNodesByStore(){
        if (dbType.equalsIgnoreCase("postgres")){
            return nodeListMapper.findNodesByStorePostgres();
        } else if (dbType.equalsIgnoreCase("mysql")){
            return nodeListMapper.findNodesByStoreMySQL();
        } else if (dbType.equalsIgnoreCase("oracle")){
            return nodeListMapper.findNodesByStoreOracle();
        } else if (dbType.equalsIgnoreCase("microsoft")){
            return nodeListMapper.findNodesByStoreMSSql();
        }

        return null;
    }

    public List findLargeFolders(){
        if (dbType.equalsIgnoreCase("postgres")){
            return largeFolderMapper.findBySize(largeFolderSize);
        } else if (dbType.equalsIgnoreCase("mysql")){
            return largeFolderMapper.findBySize(largeFolderSize);
        } else if (dbType.equalsIgnoreCase("oracle")){
            return largeFolderMapper.findBySizeOracle(largeFolderSize);
        } else if (dbType.equalsIgnoreCase("microsoft")){
            return largeFolderMapper.findBySizeMSSql(largeFolderSize);
        }

        return null;
    }

    public List findNodesByContentType(){
        if (dbType.equalsIgnoreCase("postgres")){
            return nodeListMapper.findNodesByContentTypePostgres();
        } else if (dbType.equalsIgnoreCase("mysql")){
            return nodeListMapper.findNodesByContentTypeMySQL();
        } else if (dbType.equalsIgnoreCase("oracle")){
            return nodeListMapper.findNodesByContentTypeOracle();
        } else if (dbType.equalsIgnoreCase("microsoft")){
            return nodeListMapper.findNodesByContentTypeMSSql();
        }

        return null;
    }

    public List findActivitiesByActivityType(){
        if (dbType.equalsIgnoreCase("postgres")){
            return activitiesFeedMapper.findActivitiesByActivityTypePostgres();
        } else if (dbType.equalsIgnoreCase("mysql")){
            return activitiesFeedMapper.findActivitiesByActivityTypeMySQL();
        } else if (dbType.equalsIgnoreCase("oracle")){
            return activitiesFeedMapper.findActivitiesByActivityTypeOracle();
        } else if (dbType.equalsIgnoreCase("microsoft")){
            return activitiesFeedMapper.findActivitiesByActivityTypeMSSql();
        }

        return null;
    }

    public List findActivitiesByUser(){
        if (dbType.equalsIgnoreCase("postgres")){
            return activitiesFeedMapper.findActivitiesByUserPostgres();
        } else if (dbType.equalsIgnoreCase("mysql")){
            return activitiesFeedMapper.findActivitiesByUserMySQL();
        } else if (dbType.equalsIgnoreCase("oracle")){
            return activitiesFeedMapper.findActivitiesByUserOracle();
        } else if (dbType.equalsIgnoreCase("microsoft")){
            return activitiesFeedMapper.findActivitiesByUserMSSql();
        }

        return null;
    }

    public List findActivitiesByApplicationInterface(){
        if (dbType.equalsIgnoreCase("postgres")){
            return activitiesFeedMapper.findActivitiesByApplicationInterfacePostgres();
        } else if (dbType.equalsIgnoreCase("mysql")){
            return activitiesFeedMapper.findActivitiesByApplicationInterfaceMySQL();
        } else if (dbType.equalsIgnoreCase("oracle")){
            return activitiesFeedMapper.findActivitiesByApplicationInterfaceOracle();
        } else if (dbType.equalsIgnoreCase("microsoft")){
            return activitiesFeedMapper.findActivitiesByApplicationInterfaceMSSql();
        }

        return null;
    }
}
