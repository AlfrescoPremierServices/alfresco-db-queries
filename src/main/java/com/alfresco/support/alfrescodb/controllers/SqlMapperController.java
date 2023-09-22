package com.alfresco.support.alfrescodb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alfresco.support.alfrescodb.dao.AccessControlListMapper;
import com.alfresco.support.alfrescodb.dao.ActivitiesFeedMapper;
import com.alfresco.support.alfrescodb.dao.AppliedPatchesMapper;
import com.alfresco.support.alfrescodb.dao.ArchivedNodesMapper;
import com.alfresco.support.alfrescodb.dao.AuthorityMapper;
import com.alfresco.support.alfrescodb.dao.ContentModelPropertiesMapper;
import com.alfresco.support.alfrescodb.dao.DbSizeMapper;
import com.alfresco.support.alfrescodb.dao.JmxPropertiesMapper;
import com.alfresco.support.alfrescodb.dao.LargeFolderMapper;
import com.alfresco.support.alfrescodb.dao.LargeTransactionMapper;
import com.alfresco.support.alfrescodb.dao.LockedResourcesMapper;
import com.alfresco.support.alfrescodb.dao.NodeListMapper;
import com.alfresco.support.alfrescodb.dao.SolrMemoryMapper;
import com.alfresco.support.alfrescodb.dao.WorkflowMapper;
import com.alfresco.support.alfrescodb.model.AccessControlList;
import com.alfresco.support.alfrescodb.model.ActivitiesFeed;
import com.alfresco.support.alfrescodb.model.AppliedPatches;
import com.alfresco.support.alfrescodb.model.ArchivedNodes;
import com.alfresco.support.alfrescodb.model.Authority;
import com.alfresco.support.alfrescodb.model.ContentModelProperties;
import com.alfresco.support.alfrescodb.model.IndexedList;
import com.alfresco.support.alfrescodb.model.JmxProperties;
import com.alfresco.support.alfrescodb.model.LargeFolder;
import com.alfresco.support.alfrescodb.model.LargeTransaction;
import com.alfresco.support.alfrescodb.model.LockedResources;
import com.alfresco.support.alfrescodb.model.MSSqlRelationInfo;
import com.alfresco.support.alfrescodb.model.NodesList;
import com.alfresco.support.alfrescodb.model.OracleRelationInfo;
import com.alfresco.support.alfrescodb.model.SolrMemory;
import com.alfresco.support.alfrescodb.model.Workflow;

public class SqlMapperController {

    @Value("${spring.datasource.platform}")
    private String dbType;

    @Value("${largeFolderSize}")
    private Integer largeFolderSize;

    @Value("${largeTransactionSize}")
    private Integer largeTransactionSize;

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
    private LargeTransactionMapper largeTransactionMapper;

    @Autowired
    private SolrMemoryMapper solrMemoryMapper;

    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private AppliedPatchesMapper appliedPatchesMapper;

    // XXXX
    @Autowired
    private WorkflowMapper workflowMapper;

    @Autowired
    private ArchivedNodesMapper archivedNodesMapper;

    @Autowired
    private LockedResourcesMapper lockedResourcesMapper;

    @Autowired
    private JmxPropertiesMapper jmxPropertiesMapper;

    public List findTablesInfo() {
        if (dbType.equalsIgnoreCase("postgres")) {
            return dbSizeMapper.findTablesInfoPostgres();
        } else if (dbType.equalsIgnoreCase("mysql")) {
            return dbSizeMapper.findTablesInfoMysql();
        } else if (dbType.equalsIgnoreCase("microsoft")) {
            return dbSizeMapper.findTablesInfoMSSql();
        } else if (dbType.equalsIgnoreCase("oracle")) {
            return dbSizeMapper.findTablesInfoOracle();
        }
        return null;
    }

    public List<OracleRelationInfo> findIndexesInfoOracle() {
        return dbSizeMapper.findIndexesInfoOracle();
    }

    public List<MSSqlRelationInfo> findIndexesInfoMSSql() {
        return dbSizeMapper.findIndexesInfoMSSql();
    }

    public String findDbSize() {
        if (dbType.equals("postgres")) {
            return dbSizeMapper.findDbSizePostgres();
        } else if (dbType.equalsIgnoreCase("mysql")) {
            return dbSizeMapper.findDbSizeMysql();
        }
        return null;
    }

    public List<NodesList> findNodesSize() {
        if (dbType.equalsIgnoreCase("postgres")) {
            return nodeListMapper.findNodesSize();
        } else if (dbType.equalsIgnoreCase("mysql")) {
            return nodeListMapper.findNodesSize();
        } else if (dbType.equalsIgnoreCase("oracle")) {
            return nodeListMapper.findNodesSizeOracle();
        } else if (dbType.equalsIgnoreCase("microsoft")) {
            return nodeListMapper.findNodesSizeMSSql();
        }
        return null;
    }

    public List<NodesList> findNodesSizeByMimeType() {
        if (dbType.equalsIgnoreCase("microsoft")) {
            return nodeListMapper.findNodesSizeByMimeTypeMSSql();
        } else
            return nodeListMapper.findNodesSizeByMimeType();
    }

    public List<NodesList> findNodesByStore() {
        if (dbType.equalsIgnoreCase("postgres")) {
            return nodeListMapper.findNodesByStorePostgres();
        } else if (dbType.equalsIgnoreCase("mysql")) {
            return nodeListMapper.findNodesByStoreMySQL();
        } else if (dbType.equalsIgnoreCase("oracle")) {
            return nodeListMapper.findNodesByStoreOracle();
        } else if (dbType.equalsIgnoreCase("microsoft")) {
            return nodeListMapper.findNodesByStoreMSSql();
        }
        return null;
    }

    public List<IndexedList> findNodesByTypeAndIndexing() {
        return nodeListMapper.findNodesByTypeAndIndexing();
    }

    public List<LargeFolder> findLargeFolders() {
        return largeFolderMapper.findBySize(largeFolderSize);
    }

    public List<LargeTransaction> findLargeTransactions() {
        return largeTransactionMapper.findBySize(largeTransactionSize);
    }

    public List<NodesList> findNodesByContentType() {
        if (dbType.equalsIgnoreCase("postgres")) {
            return nodeListMapper.findNodesByContentTypePostgres();
        } else if (dbType.equalsIgnoreCase("mysql")) {
            return nodeListMapper.findNodesByContentTypeMySQL();
        } else if (dbType.equalsIgnoreCase("oracle")) {
            return nodeListMapper.findNodesByContentTypeOracle();
        } else if (dbType.equalsIgnoreCase("microsoft")) {
            return nodeListMapper.findNodesByContentTypeMSSql();
        }
        return null;
    }

    public List<NodesList> findNodesByContentTypeAndMonth() {
        if (dbType.equalsIgnoreCase("postgres")) {
            return nodeListMapper.findNodesByContentTypeAndMonthPostgres();
        } else if (dbType.equalsIgnoreCase("mysql")) {
            return nodeListMapper.findNodesByContentTypeAndMonthMySQL();
        } else if (dbType.equalsIgnoreCase("oracle")) {
            return nodeListMapper.findNodesByContentTypeAndMonthOracle();
        } else if (dbType.equalsIgnoreCase("microsoft")) {
            return nodeListMapper.findNodesByContentTypeAndMonthMSSql();
        }
        return null;
    }

    public String findAccessControlList() {
        return accessControlListMapper.findAccessControlList();
    }

    public List<AccessControlList> findAccessControlListEntries() {
        return accessControlListMapper.findAccessControlListEntries();
    }

    public List<AccessControlList> findACLNodeRepartition() {
        if (dbType.equalsIgnoreCase("oracle")) {
            return accessControlListMapper.findACLNodeRepartitionOracle();
        } else if (dbType.equalsIgnoreCase("microsoft")) {
            return accessControlListMapper.findACLNodeRepartitionMSSql();
        } else
            return accessControlListMapper.findACLNodeRepartition();
    }

    public List<AccessControlList> findACEAuthorities() {
        if (dbType.equalsIgnoreCase("oracle")) {
            return accessControlListMapper.findACEAuthoritiesOracle();
        } else if (dbType.equalsIgnoreCase("microsoft")) {
            return accessControlListMapper.findACEAuthoritiesMSSql();
        } else
            return accessControlListMapper.findACEAuthorities();
    }

    public String findOrphanedAcls() {
        return accessControlListMapper.findOrphanedAcls();
    }

    public List<AccessControlList> findAclTypeRepartition() {
        return accessControlListMapper.findAclTypesRepartition();
    }

    public List<AccessControlList> findAclsHeight() {
        return accessControlListMapper.findAclsHeight();
    }

    public List<ContentModelProperties> findContentModelProperties() {
        return contentModelPropertiesMapper.findContentModelProperties();
    }

    public List<ActivitiesFeed> findActivitiesByActivityType() {
        if (dbType.equalsIgnoreCase("postgres")) {
            return activitiesFeedMapper.findActivitiesByActivityTypePostgres();
        } else if (dbType.equalsIgnoreCase("mysql")) {
            return activitiesFeedMapper.findActivitiesByActivityTypeMySQL();
        } else if (dbType.equalsIgnoreCase("oracle")) {
            return activitiesFeedMapper.findActivitiesByActivityTypeOracle();
        } else if (dbType.equalsIgnoreCase("microsoft")) {
            return activitiesFeedMapper.findActivitiesByActivityTypeMSSql();
        }
        return null;
    }

    public List<ActivitiesFeed> findActivitiesByUser() {
        if (dbType.equalsIgnoreCase("postgres")) {
            return activitiesFeedMapper.findActivitiesByUserPostgres();
        } else if (dbType.equalsIgnoreCase("mysql")) {
            return activitiesFeedMapper.findActivitiesByUserMySQL();
        } else if (dbType.equalsIgnoreCase("oracle")) {
            return activitiesFeedMapper.findActivitiesByUserOracle();
        } else if (dbType.equalsIgnoreCase("microsoft")) {
            return activitiesFeedMapper.findActivitiesByUserMSSql();
        }
        return null;
    }

    public List<ActivitiesFeed> findActivitiesByApplicationInterface() {
        if (dbType.equalsIgnoreCase("postgres")) {
            return activitiesFeedMapper.findActivitiesByApplicationInterfacePostgres();
        } else if (dbType.equalsIgnoreCase("mysql")) {
            return activitiesFeedMapper.findActivitiesByApplicationInterfaceMySQL();
        } else if (dbType.equalsIgnoreCase("oracle")) {
            return activitiesFeedMapper.findActivitiesByApplicationInterfaceOracle();
        } else if (dbType.equalsIgnoreCase("microsoft")) {
            return activitiesFeedMapper.findActivitiesByApplicationInterfaceMSSql();
        }
        return null;
    }

    public List<SolrMemory> solrMemory() {
        return solrMemoryMapper.solrMemory();
    }

    public List<Authority> findAuthorizedUsers() {
        if (dbType.equalsIgnoreCase("postgres")) {
            return authorityMapper.findAuthorizedUsers();
        } else if (dbType.equalsIgnoreCase("mysql")) {
            return authorityMapper.findAuthorizedUsers();
        } else if (dbType.equalsIgnoreCase("oracle")) {
            return authorityMapper.findAuthorizedUsersOracle();
        } else if (dbType.equalsIgnoreCase("microsoft")) {
            return authorityMapper.findAuthorizedUsersMSSql();
        }
        return null;
    }

    public List<Authority> findUsers() {
        return authorityMapper.findUsers();
    }

    public List<Authority> findGroups() {
        return authorityMapper.findGroups();
    }

    // Applied Patches
    public List<AppliedPatches> findAppliedPatches() {
        return appliedPatchesMapper.findAppliedPatches();
    }

    // Workflows
    public List<Workflow> findAllWorkflows() {
        return workflowMapper.findAll();
    }

    public List<Workflow> openWorkflows() {
        return workflowMapper.openWorkflows();
    }

    public List<Workflow> closedWorkflows() {
        return workflowMapper.closedWorkflows();
    }

    public List<Workflow> openTasks() {
        return workflowMapper.openTasks();
    }

    public List<Workflow> closedTasks() {
        return workflowMapper.closedTasks();
    }

    // Archived Nodes
    public List<ArchivedNodes> findArchivedNodes() {
        return archivedNodesMapper.findArchivedNodes();
    }

    public List<ArchivedNodes> findArchivedNodesByUser() {
        return archivedNodesMapper.findArchivedNodesByUser();
    }

    // Locked Resources
    public List<LockedResources> findAllLockedResources() {
        return lockedResourcesMapper.findAll();
    }

    // JMX Properties
    public List<JmxProperties> findJmxProperties() {
        return jmxPropertiesMapper.findJmxProperties();
    }
}
