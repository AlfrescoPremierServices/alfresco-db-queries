package com.alfresco.support.alfrescodb.controllers;

import com.alfresco.support.alfrescodb.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;

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
    private AccessControlListMapper accessControlListMapper;

    @Autowired
    private ContentModelPropertiesMapper contentModelPropertiesMapper;

    @Autowired
    private LargeFolderMapper largeFolderMapper;

    @Autowired
    private SolrMemoryMapper solrMemoryMapper;

    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private AppliedPatchesMapper appliedPatchesMapper;

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

    public List findNodesByContentTypeAndMonth(){
        if (dbType.equalsIgnoreCase("postgres")){
            return nodeListMapper.findNodesByContentTypeAndMonthPostgres();
        } else if (dbType.equalsIgnoreCase("mysql")){
            return nodeListMapper.findNodesByContentTypeAndMonthMySQL();
        } else if (dbType.equalsIgnoreCase("oracle")){
            return nodeListMapper.findNodesByContentTypeAndMonthOracle();
        } else if (dbType.equalsIgnoreCase("microsoft")){
            return nodeListMapper.findNodesByContentTypeAndMonthMSSql();
        }

        return null;
    }

    public String findAccessControlList(){
        return accessControlListMapper.findAccessControlList();
    }

    public List findAccessControlListEntries(){
        return accessControlListMapper.findAccessControlListEntries();
    }

    public List findACLNodeRepartition() {
        return accessControlListMapper.findACLNodeRepartition();
    }

    public List findACEAuthorities() {
        return accessControlListMapper.findACEAuthorities();
    }

    public String findOrphanedAcls() {
        return accessControlListMapper.findOrphanedAcls();
    }

    public List findAclTypeRepartition() {
        return accessControlListMapper.findAclTypesRepartition();
    }

    public List findAclsHeight() {
        return accessControlListMapper.findAclsHeight();
    }

    public List findContentModelProperties(){
        return contentModelPropertiesMapper.findContentModelProperties();
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

    public List solrMemory(){
        return solrMemoryMapper.solrMemory();
    }

    public List findAuthorizedUsers(){
        if (dbType.equalsIgnoreCase("postgres")){
            return authorityMapper.findAuthorizedUsers();
        } else if (dbType.equalsIgnoreCase("mysql")){
            return authorityMapper.findAuthorizedUsers();
        } else if (dbType.equalsIgnoreCase("oracle")){
            return authorityMapper.findAuthorizedUsersOracle();
        } else if (dbType.equalsIgnoreCase("microsoft")){
            return authorityMapper.findAuthorizedUsersMSSql();
        }

        return null;
    }

    //Applied Patches
    public List findAppliedPatches(){
        return appliedPatchesMapper.findAppliedPatches();
    }


}
