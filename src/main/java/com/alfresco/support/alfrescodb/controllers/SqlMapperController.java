package com.alfresco.support.alfrescodb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alfresco.support.alfrescodb.dao.AccessControlListMapper;
import com.alfresco.support.alfrescodb.dao.ActivitiesFeedMapper;
import com.alfresco.support.alfrescodb.dao.AppliedPatchesMapper;
import com.alfresco.support.alfrescodb.dao.AuthorityMapper;
import com.alfresco.support.alfrescodb.dao.ContentModelPropertiesMapper;
import com.alfresco.support.alfrescodb.dao.DbSizeMapper;
import com.alfresco.support.alfrescodb.dao.LargeFolderMapper;
import com.alfresco.support.alfrescodb.dao.NodeListMapper;
import com.alfresco.support.alfrescodb.dao.SolrMemoryMapper;
import com.alfresco.support.alfrescodb.model.AccessControlList;
import com.alfresco.support.alfrescodb.model.ActivitiesFeed;
import com.alfresco.support.alfrescodb.model.AppliedPatches;
import com.alfresco.support.alfrescodb.model.Authority;
import com.alfresco.support.alfrescodb.model.ContentModelProperties;
import com.alfresco.support.alfrescodb.model.LargeFolder;
import com.alfresco.support.alfrescodb.model.NodesList;
import com.alfresco.support.alfrescodb.model.SolrMemory;

public class SqlMapperController {

    @Value("${spring.datasource.platform}")
    private String dbType;
    
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

    public List < NodesList > findNodesSize(){
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

    public List < NodesList > findNodesSizeByMimeType(){
        if (dbType.equalsIgnoreCase("microsoft")){
            return nodeListMapper.findNodesSizeByMimeTypeMSSql();
        } else return nodeListMapper.findNodesSizeByMimeType();
    }

    public List < NodesList > findNodesByStore(){
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

    public List< LargeFolder > findLargeFolders(Integer largeFolderSize){
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

    public List < NodesList > findNodesByContentType(){
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

    public List < NodesList > findNodesByContentTypeAndMonth(){
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

    public List< AccessControlList > findAccessControlListEntries(){
        return accessControlListMapper.findAccessControlListEntries();
    }

    public List< AccessControlList > findACLNodeRepartition() {
        if (dbType.equalsIgnoreCase("oracle")){
            return accessControlListMapper.findACLNodeRepartitionOracle();
	} else if (dbType.equalsIgnoreCase("microsoft")){
            return accessControlListMapper.findACLNodeRepartitionMSSql();
	} else return accessControlListMapper.findACLNodeRepartition();
    }

    public List< AccessControlList > findACEAuthorities() {
        if (dbType.equalsIgnoreCase("oracle")){
            return accessControlListMapper.findACEAuthoritiesOracle();
	} else if (dbType.equalsIgnoreCase("microsoft")){
            return accessControlListMapper.findACEAuthoritiesMSSql();
	} else return accessControlListMapper.findACEAuthorities();
    }

    public String findOrphanedAcls() {
        return accessControlListMapper.findOrphanedAcls();
    }

    public List< AccessControlList > findAclTypeRepartition() {
        return accessControlListMapper.findAclTypesRepartition();
    }

    public List< AccessControlList > findAclsHeight() {
        return accessControlListMapper.findAclsHeight();
    }

    public List < ContentModelProperties > findContentModelProperties(){
        return contentModelPropertiesMapper.findContentModelProperties();
    }

    public List < ActivitiesFeed > findActivitiesByActivityType(){
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

    public List < ActivitiesFeed > findActivitiesByUser(){
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

    public List < ActivitiesFeed > findActivitiesByApplicationInterface(){
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

    public List < SolrMemory > solrMemory(){
        return solrMemoryMapper.solrMemory();
    }

    public List < Authority > findAuthorizedUsers(){
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
    public List<AppliedPatches> findAppliedPatches(){
        return appliedPatchesMapper.findAppliedPatches();
    }


}
