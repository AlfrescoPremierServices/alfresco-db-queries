package com.alfresco.support.alfrescodb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alfresco.support.alfrescodb.DAOMapper;
import com.alfresco.support.alfrescodb.beans.AccessControlBean;
import com.alfresco.support.alfrescodb.beans.ActivitiesFeedByApplication;
import com.alfresco.support.alfrescodb.beans.ActivitiesFeedByTypeBean;
import com.alfresco.support.alfrescodb.beans.ActivitiesFeedByUserBean;
import com.alfresco.support.alfrescodb.beans.AppliedPatchesBean;
import com.alfresco.support.alfrescodb.beans.ArchivedNodesBean;
import com.alfresco.support.alfrescodb.beans.ContentModelBean;
import com.alfresco.support.alfrescodb.beans.DbMsSQLIndexBean;
import com.alfresco.support.alfrescodb.beans.DbMsSQLTableBean;
import com.alfresco.support.alfrescodb.beans.DbMySQLBean;
import com.alfresco.support.alfrescodb.beans.DbPostgresBean;
import com.alfresco.support.alfrescodb.beans.JmxPropertiesBean;
import com.alfresco.support.alfrescodb.beans.LargeFolderBean;
import com.alfresco.support.alfrescodb.beans.LargeTransactionBean;
import com.alfresco.support.alfrescodb.beans.LockedResourcesBean;
import com.alfresco.support.alfrescodb.beans.NodeContentTypeBean;
import com.alfresco.support.alfrescodb.beans.NodeContentTypeMonthBean;
import com.alfresco.support.alfrescodb.beans.NodeMimeTypeBean;
import com.alfresco.support.alfrescodb.beans.NodeStoreBean;
import com.alfresco.support.alfrescodb.beans.WorkflowBean;
import com.alfresco.support.alfrescodb.config.DbQueriesProperties;
import com.alfresco.support.alfrescodb.export.ExportComponent;

@Controller
public class WebController {
    @Autowired
    DbQueriesProperties appProperties;

    @Autowired
    ExportComponent exportController;

    @Autowired
    private DAOMapper exportMapper;

    @RequestMapping("/")
    public String index(String name, Model model) {
        //add some value to show in the index page
        model.addAttribute("largeFolderSize", appProperties.getLargeFolderSizeThreshold());
        model.addAttribute("largeTransactionSize", appProperties.getLargeTransactionSizeThreshold());
        return "index";
    }

    @RequestMapping("/report")
    public void report(Model model) {
        exportController.exportReport(model);
    }

    @RequestMapping("/appliedPatches")
    public void appliedPatches(Model model) {
        List<AppliedPatchesBean> listAppliedPatches = exportMapper.listAppliedPatches();
        model.addAttribute("listAppliedPatches", listAppliedPatches);
    }

    @RequestMapping("/largeFolders")
    public void largeFolders(Model model) {
        List<LargeFolderBean> listLargeFolders = exportMapper.findLargeFolders(appProperties.getLargeFolderSizeThreshold());
        model.addAttribute("listLargeFolders", listLargeFolders);
        model.addAttribute("size", appProperties.getLargeFolderSizeThreshold());
    }

    @RequestMapping("/largeTransactions")
    public void largeTransactions(Model model) {
        List<LargeTransactionBean> listLargeTransactions = exportMapper.findLargeTransactions(appProperties.getLargeTransactionSizeThreshold());
        model.addAttribute("listLargeTransactions", listLargeTransactions);
        model.addAttribute("size", appProperties.getLargeTransactionSizeThreshold());
    }

    @RequestMapping("/dbSize")
    public String dbSize(Model model) {
        /* DBSize */
        if ("postgres".equalsIgnoreCase(appProperties.getDbType())) {
            List<DbPostgresBean> listDbPostgres = exportMapper.findTablesInfoPostgres();
            model.addAttribute("listRelationInfosPostgres", listDbPostgres);
        } else if ("mysql".equalsIgnoreCase(appProperties.getDbType())) {
            List<DbMySQLBean> listDbMySQL = exportMapper.findTablesInfoMysql();
            model.addAttribute("listRelationInfosMySQL", listDbMySQL);
        //} else if ("oracle".equalsIgnoreCase(appProperties.getDbType())) {
            // XXX TODO
        } else if ("microsoft".equalsIgnoreCase(appProperties.getDbType())) {
            List<DbMsSQLTableBean> listMsSQLTable = exportMapper.findTablesInfoMSSql();
            model.addAttribute("listMsSQLTable", listMsSQLTable);
            List<DbMsSQLIndexBean> listMsSQLIndex = exportMapper.findIndexesInfoMSSql();
            model.addAttribute("listMsSQLIndex", listMsSQLIndex);
        } else {
            throw new IllegalArgumentException("DB Type not recognized: " + appProperties.getDbType());
        }
        return null;
    }

    @RequestMapping("/workflows")
    public void workflows(Model model) {
        // Count workflows by process def and task name
        List<WorkflowBean> listWorkflows = exportMapper.listWorkflowsWithProcessesAndTasks();
        model.addAttribute("listWorkflows", listWorkflows);

        // Count open processes
        List<WorkflowBean> listOpenWorkflows = exportMapper.listOpenWorkflows();
        model.addAttribute("listOpenWorkflows", listOpenWorkflows);

        // Count closed processes
        List<WorkflowBean> listClosedWorkflows = exportMapper.listClosedWorkflows();
        model.addAttribute("listClosedWorkflows", listClosedWorkflows);

        // Count open taks
        List<WorkflowBean> listOpenTasks = exportMapper.listOpenTasks();
        model.addAttribute("listOpenTasks", listOpenTasks);

        // Count closed tasks
        List<WorkflowBean> listClosedTasks = exportMapper.listClosedTasks();
        model.addAttribute("listClosedTasks", listClosedTasks);
    }

    @RequestMapping("/accessControlList")
    public void accessControlList(Model model) {
        // Some count
        String aclSize = exportMapper.countTotalAcls();
        String orphanedAcls = exportMapper.countTotalOrphanedAcls();
        model.addAttribute("aclSize", aclSize);
        model.addAttribute("orphanedAcls", orphanedAcls);
        
        // ACL IDs more used
        List<AccessControlBean> listACLs;
        if ("oracle".equalsIgnoreCase(appProperties.getDbType())) {
            listACLs = exportMapper.findACLNodeRepartitionOracle();
        } else if ("microsoft".equalsIgnoreCase(appProperties.getDbType())) {
            listACLs = exportMapper.findACLNodeRepartitionMSSql();
        } else {
            listACLs = exportMapper.findACLNodeRepartition();
        }
        model.addAttribute("aclNodeRepartition", listACLs);

        // Nodes per ACL Types (aka: DEFINING, SHARED, ...)
        List<AccessControlBean> listAclTypeRepartition = exportMapper.findAclTypesRepartition();
        model.addAttribute("listAclTypeRepartition", listAclTypeRepartition);

        // ACEs by permission
        List<AccessControlBean> listAccessControlListEntries = exportMapper.findAccessControlListEntries();
        model.addAttribute("listAccessControlListEntries", listAccessControlListEntries);
        // Count total permissions
        Integer aceSize = 0;
        for (int i = 0; i < listAccessControlListEntries.size(); i++) {
            Integer count = Integer.valueOf(listAccessControlListEntries.get(i).getCount());
            aceSize = aceSize + count;
        }
        model.addAttribute("aceSize", aceSize);

        // ACEs by Authority (Group/User names are hashed)
        List<AccessControlBean> listACEAuthorities;
        if ("oracle".equalsIgnoreCase(appProperties.getDbType())) {
            listACEAuthorities = exportMapper.findACEAuthoritiesOracle();
        } else if ("microsoft".equalsIgnoreCase(appProperties.getDbType())) {
            listACEAuthorities = exportMapper.findACEAuthoritiesMSSql();
        } else {
            listACEAuthorities = exportMapper.findACEAuthorities();
        }
        model.addAttribute("listACEAuthorities", listACEAuthorities);

        // ACL Height
        List<AccessControlBean> listAclsHeight = exportMapper.findAclsHeight();
        model.addAttribute("listAclsHeight", listAclsHeight);
    }

    @RequestMapping("/contentModelProperties")
    public void contentModelProperties(Model model) {
        List<ContentModelBean> contentModelBean = exportMapper.listContentModels();
        model.addAttribute("listContentModelProperties", contentModelBean);
    }

    @RequestMapping("/activitiesFeed")
    public void activitiesFeed(Model model) {
        // Activities by activity type
        List<ActivitiesFeedByTypeBean> activitiesFeedType = exportMapper.listActivitiesByActivityType();
        model.addAttribute("listActivitiesFeedByActivityType", activitiesFeedType);
        
        // Activities by user
        List<ActivitiesFeedByUserBean> activitiesFeedByUser = exportMapper.listActivitiesByUser();
        model.addAttribute("listActivitiesFeedByUser", activitiesFeedByUser);
        
        // Activities by application interface
        List<ActivitiesFeedByApplication> activitiesFeedByApplication = exportMapper.listActivitiesByApplication();
        model.addAttribute("listActivitiesFeedByAppTool", activitiesFeedByApplication);
    }

    @RequestMapping("/archivedNodes")
    public void archivedNodes(Model model) {
        List<ArchivedNodesBean> archivedNodesByUser = exportMapper.listArchivedNodesByUser();
        String totArchivedNodes = exportMapper.countTotalArchivedNodes();
        model.addAttribute("listArchivedNodes", archivedNodesByUser);
        model.addAttribute("totArchivedNodes", totArchivedNodes);
    }

    @RequestMapping("/listNodesByMimeType")
    public void nodesByMimeType(Model model) {
        List<NodeMimeTypeBean> listNodesByMimeType = exportMapper.listActiveNodesByMimetype();
        model.addAttribute("listNodesByMimeType", listNodesByMimeType);
    }

    @RequestMapping("/listNodesByType")
    public void nodesByType(Model model) {
        List<NodeContentTypeBean> listNodesByType = exportMapper.listActiveNodesByContentType();
        model.addAttribute("listNodesByType", listNodesByType);
    }

    @RequestMapping("/listNodesByTypeAndMonth")
    public void listNodesByTypeAndMonth(Model model) {
        List<NodeContentTypeMonthBean> listNodesByTypeAndMonth = exportMapper.listActiveNodesByContentTypeAndMonth();
        model.addAttribute("listNodesByTypeAndMonth", listNodesByTypeAndMonth);
    }

    @RequestMapping("/listNodesByStore")
    public void nodesByStore(Model model) {
        List<NodeStoreBean> listNodesByStore = exportMapper.listNodesByStore();
        model.addAttribute("listNodesByStore", listNodesByStore);
    }

    @RequestMapping("/lockedResources")
    public void lockedResources(Model model) {
        List<LockedResourcesBean> listLockedResources = exportMapper.lockedResources();
        model.addAttribute("listLockedResources", listLockedResources);
    }

    @RequestMapping("/authorities")
    public void authorities(Model model) {
        // Count users
        String countTotalUsers = exportMapper.countTotalUsers();
        model.addAttribute("countTotalUsers", countTotalUsers);

        if (appProperties.getIsEnterpriseVersion()) {
            // Count authorized users
            String countAuthorizedUsers = "";
            if ("oracle".equalsIgnoreCase(appProperties.getDbType())) {
                //NOOP
            } else if ("microsoft".equalsIgnoreCase(appProperties.getDbType())) {
                countAuthorizedUsers = exportMapper.countAuthorizedUsersMicrosoft();
            } else {
                countAuthorizedUsers = exportMapper.countAuthorizedUsers();
            }

            model.addAttribute("countAuthorizedUsers", countAuthorizedUsers);
            model.addAttribute("isEnterpriseVersion", appProperties.getIsEnterpriseVersion());
        }
        // Count groups
        String countGroups = exportMapper.countGroups();
        model.addAttribute("countGroups", countGroups);
    }

    @RequestMapping("/jmxProperties")
    public void jmxProperties(Model model) {
        List<JmxPropertiesBean> listJmxProperties = exportMapper.findJmxProperties();
        model.addAttribute("listJmxProperties", listJmxProperties);
    }
}