package com.alfresco.support.alfrescodb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
    @Value("${largeFolderSize}")
    private String largeFolderSize;

    @Value("${largeTransactionSize}")
    private String largeTransactionSize;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    ResultSet resultSet;

    @RequestMapping("/")
    public String index(String name, Model model) {
        addAdditionalParamsToModel(model);
        return "index";
    }

    
    @RequestMapping("/workflows")
    public String workflows(Model model) {

    	// Count workflows by process def and task name
        String sql = "select count(*) as occurrencies, proc_def_id_, name_ " +
		"FROM act_hi_taskinst " +
		"GROUP BY proc_def_id_, name_ " +
		"ORDER BY occurrencies desc";
        List < Workflow > listWorkflows = jdbcTemplate.query(sql, new RowMapper < Workflow > () {
            public Workflow mapRow(ResultSet result, int rowNum) throws SQLException {
            	Workflow workflow = new Workflow();
            	workflow.setOccurrencies(result.getInt("occurrencies"));
            	workflow.setProcDefId(result.getString("proc_def_id_"));
            	workflow.setName(result.getString("name_"));

                return workflow;
            }
        });

        model.addAttribute("listWorkflows", listWorkflows);

    	// Count open processes
        sql = "select count(proc_def_id_) as occurrencies, proc_def_id_ " +
        "FROM act_hi_procinst " +
        "WHERE end_time_ is null " +
		"GROUP BY proc_def_id_ " +
		"ORDER BY occurrencies desc";
        List < Workflow > listOpenWorkflows = jdbcTemplate.query(sql, new RowMapper < Workflow > () {
            public Workflow mapRow(ResultSet result, int rowNum) throws SQLException {
            	Workflow workflow = new Workflow();
            	workflow.setOccurrencies(result.getInt("occurrencies"));
            	workflow.setProcDefId(result.getString("proc_def_id_"));

                return workflow;
            }
        });

        model.addAttribute("listOpenWorkflows", listOpenWorkflows);
        
    	// Count closed processes
        sql = "select count(proc_def_id_) as occurrencies, proc_def_id_ " +
        "FROM act_hi_procinst " +
        "WHERE end_time_ is not null " +
		"GROUP BY proc_def_id_ " +
		"ORDER BY occurrencies desc";
        List < Workflow > listClosedWorkflows = jdbcTemplate.query(sql, new RowMapper < Workflow > () {
            public Workflow mapRow(ResultSet result, int rowNum) throws SQLException {
            	Workflow workflow = new Workflow();
            	workflow.setOccurrencies(result.getInt("occurrencies"));
            	workflow.setProcDefId(result.getString("proc_def_id_"));

                return workflow;
            }
        });

        model.addAttribute("listClosedWorkflows", listClosedWorkflows);
        
    	// Count open taks
        sql = "select count(proc_def_id_) as occurrencies, proc_def_id_, name_ " +
        "FROM act_hi_taskinst " +
        "WHERE end_time_ is null " +
		"GROUP BY proc_def_id_, name_ " +
		"ORDER BY occurrencies desc";
        List < Workflow > listOpenTasks = jdbcTemplate.query(sql, new RowMapper < Workflow > () {
            public Workflow mapRow(ResultSet result, int rowNum) throws SQLException {
            	Workflow workflow = new Workflow();
            	workflow.setOccurrencies(result.getInt("occurrencies"));
            	workflow.setProcDefId(result.getString("proc_def_id_"));
            	workflow.setName(result.getString("name_"));

                return workflow;
            }
        });

        model.addAttribute("listOpenTasks", listOpenTasks);
        
    	// Count closed tasks
        sql = "select count(proc_def_id_) as occurrencies, proc_def_id_, name_ " +
        "FROM act_hi_taskinst " +
        "WHERE end_time_ is not null " +
		"GROUP BY proc_def_id_, name_ " +
		"ORDER BY occurrencies desc";
        List < Workflow > listClosedTasks = jdbcTemplate.query(sql, new RowMapper < Workflow > () {
            public Workflow mapRow(ResultSet result, int rowNum) throws SQLException {
            	Workflow workflow = new Workflow();
            	workflow.setOccurrencies(result.getInt("occurrencies"));
            	workflow.setProcDefId(result.getString("proc_def_id_"));
            	workflow.setName(result.getString("name_"));

                return workflow;
            }
        });

        model.addAttribute("listClosedTasks", listClosedTasks);
        
        addAdditionalParamsToModel(model);

        return null;
    }
    
    @RequestMapping("/dbSize")
    public String dbSize(Model model) {

        String tblSzSql = "SELECT nspname || '.' || relname AS relation," +
		"pg_size_pretty(pg_total_relation_size(C.oid)) AS total_size " +
		"FROM pg_class C " +
		"LEFT JOIN pg_namespace N ON (N.oid = C.relnamespace)" +
		"WHERE nspname NOT IN ('pg_catalog', 'information_schema')" +
		"AND C.relkind <> 'i' AND nspname !~ '^pg_toast'" +
		"ORDER BY pg_total_relation_size(C.oid) DESC LIMIT 10";
        List < RelationInfo > listRelationInfos = jdbcTemplate.query(tblSzSql, new RowMapper < RelationInfo > () {
            public RelationInfo mapRow(ResultSet result, int rowNum) throws SQLException {
            	RelationInfo relationInfo = new RelationInfo();
            	relationInfo.setRelName(result.getString("relation"));
            	relationInfo.setRelSize(result.getString("total_size"));

                return relationInfo;
            }
        });

        String dbSzSql = "SELECT pg_catalog.pg_size_pretty(pg_catalog.pg_database_size(current_database()));";
        String dbSize = jdbcTemplate.queryForObject(dbSzSql, String.class);

        model.addAttribute("listRelationInfos", listRelationInfos);
        model.addAttribute("dbSize", dbSize);

        addAdditionalParamsToModel(model);

        return null;
    }

    @RequestMapping("/largeFolders")
    public String largeFolders(@RequestParam(value = "size", required = true) String size, Model model) {

        String sql = "SELECT count(*) as occurrencies, (stores.protocol || '://' || stores.identifier || '/' || nodes.uuid) as nodeRef, props.string_value as name, qname.local_name " +
            "FROM alf_node as nodes, alf_store as stores, alf_child_assoc as children, alf_node_properties as props, alf_qname as qname " +
            "WHERE children.parent_node_id=nodes.id and " +
            "stores.id=nodes.store_id and " +
            "props.node_id = nodes.id and " +
            "props.qname_id IN (SELECT id FROM alf_qname WHERE local_name = 'name') and " +
            "qname.id = nodes.type_qname_id " +
            "GROUP BY nodeRef, name, local_name " +
            "HAVING count(*) > " + size + " " +
            "ORDER BY occurrencies desc";
        List < LargeFolder > listLargeFolders = jdbcTemplate.query(sql, new RowMapper < LargeFolder > () {
            public LargeFolder mapRow(ResultSet result, int rowNum) throws SQLException {
                LargeFolder largeFolder = new LargeFolder();
                largeFolder.setOccurrencies(result.getInt("occurrencies"));
                largeFolder.setNodeRef(result.getString("nodeRef"));
                largeFolder.setName(result.getString("name"));
                largeFolder.setType(result.getString("local_name"));
                largeFolder.setEntries(result.getFetchSize());

                return largeFolder;
            }
        });

        model.addAttribute("largeFolderSize", largeFolderSize);
        model.addAttribute("listLargeFolders", listLargeFolders);
        model.addAttribute("size", size);
        
        addAdditionalParamsToModel(model);

        return null;
    }

    @RequestMapping("/largeTransactions")
    public String largeTransactions(@RequestParam(value = "size", required = true) String size, Model model) {

        String sql = "SELECT trx.id, count(*) as nodes " +
            "FROM alf_transaction trx, alf_node an " +
            "WHERE an.transaction_id = trx.id " +
            "GROUP BY trx.id " +
            "HAVING count(*) > " + size + " " +
            "ORDER BY nodes desc";
        List < LargeTransaction > listLargeTransactions = jdbcTemplate.query(sql, new RowMapper < LargeTransaction > () {
            public LargeTransaction mapRow(ResultSet result, int rowNum) throws SQLException {
            	LargeTransaction largeTransaction = new LargeTransaction();
            	largeTransaction.setNodes(result.getInt("nodes"));
            	largeTransaction.setTrxId(result.getInt("id"));
            	largeTransaction.setEntries(result.getFetchSize());

                return largeTransaction;
            }
        });

        model.addAttribute("largeTransactionSize", largeTransactionSize);
        model.addAttribute("listLargeTransactions", listLargeTransactions);
        model.addAttribute("size", size);

        addAdditionalParamsToModel(model);

        return null;
    }

    @RequestMapping("/activitiesFeed")
    public String activitiesFeed(Model model) {

        // Activities by application activity type
        String sql = "select count(*) as occurrencies, to_char(post_date, 'YYYY-Mon-DD') as date, site_network, activity_type " +
            "from alf_activity_feed " +
            "where feed_user_id != '@@NULL@@' " +
            "and feed_user_id = post_user_id " +
            "group by date, site_network, activity_type";
        
        List < ActivitiesFeed > listActivitiesFeed = jdbcTemplate.query(sql, new RowMapper < ActivitiesFeed > () {
            public ActivitiesFeed mapRow(ResultSet result, int rowNum) throws SQLException {
                ActivitiesFeed activitiesFeed = new ActivitiesFeed();
                activitiesFeed.setOccurrencies(result.getInt("occurrencies"));
                activitiesFeed.setDate(result.getString("date"));
                activitiesFeed.setSiteNetwork(result.getString("site_network"));
                activitiesFeed.setActivityType(result.getString("activity_type"));
                activitiesFeed.setEntries(result.getFetchSize());

                return activitiesFeed;
            }
        });

        model.addAttribute("listActivitiesFeedByActivityType", listActivitiesFeed);

        // Activities by application user        
        sql = "select count(*) as occurrencies, to_char(post_date, 'YYYY-Mon-DD') as date, site_network, feed_user_id " +
            "from alf_activity_feed " +
            "where feed_user_id != '@@NULL@@' " +
            "and feed_user_id = post_user_id " +
            "group by date, site_network, feed_user_id";
        
        listActivitiesFeed = jdbcTemplate.query(sql, new RowMapper < ActivitiesFeed > () {
            public ActivitiesFeed mapRow(ResultSet result, int rowNum) throws SQLException {
                ActivitiesFeed activitiesFeed = new ActivitiesFeed();
                activitiesFeed.setOccurrencies(result.getInt("occurrencies"));
                activitiesFeed.setDate(result.getString("date"));
                activitiesFeed.setSiteNetwork(result.getString("site_network"));
                activitiesFeed.setFeedUserId(result.getString("feed_user_id"));
                activitiesFeed.setEntries(result.getFetchSize());

                return activitiesFeed;
            }
        });

        model.addAttribute("listActivitiesFeedByUser", listActivitiesFeed);

        // Activities by application interface
        sql = "select count(*) as occurrencies, to_char(post_date, 'YYYY-Mon-DD') as date, site_network, app_tool " +
                "from alf_activity_feed " +
                "where feed_user_id != '@@NULL@@' " +
                "and feed_user_id = post_user_id " +
                "group by date, site_network, app_tool";
        
        listActivitiesFeed = jdbcTemplate.query(sql, new RowMapper < ActivitiesFeed > () {
            public ActivitiesFeed mapRow(ResultSet result, int rowNum) throws SQLException {
                ActivitiesFeed activitiesFeed = new ActivitiesFeed();
                activitiesFeed.setOccurrencies(result.getInt("occurrencies"));
                activitiesFeed.setDate(result.getString("date"));
                activitiesFeed.setSiteNetwork(result.getString("site_network"));
                activitiesFeed.setAppTool(result.getString("app_tool"));
                activitiesFeed.setEntries(result.getFetchSize());

                return activitiesFeed;
            }
        });

        model.addAttribute("listActivitiesFeedByAppTool", listActivitiesFeed);
          
        addAdditionalParamsToModel(model);

        return null;
    }

    @RequestMapping("/archivedNodes")
    public String archivedNodes(Model model) {

        // Archived nodes
        String sql = "select count(*) as nodes " +
            "from alf_node " +
            "where store_id in (select id from alf_store where protocol = 'archive' and identifier = 'SpacesStore')";
        
        List < ArchivedNodes > listArchivedNodes = jdbcTemplate.query(sql, new RowMapper < ArchivedNodes > () {
            public ArchivedNodes mapRow(ResultSet result, int rowNum) throws SQLException {
                ArchivedNodes archivedNodes = new ArchivedNodes();
                archivedNodes.setNodes(result.getInt("nodes"));
                archivedNodes.setEntries(result.getFetchSize());

                return archivedNodes;
            }
        });
        
        model.addAttribute("listArchivedNodes", listArchivedNodes);

        // Archived nodes by user
        sql = "select audit_modifier, count(*) as nodes " +
            "from alf_node " +
            "where store_id in (select id from alf_store where protocol = 'archive' and identifier = 'SpacesStore') " +
            "group by audit_modifier ";
        
        listArchivedNodes = jdbcTemplate.query(sql, new RowMapper < ArchivedNodes > () {
            public ArchivedNodes mapRow(ResultSet result, int rowNum) throws SQLException {
                ArchivedNodes archivedNodes = new ArchivedNodes();
                archivedNodes.setAuditModifier(result.getString("audit_modifier"));
                archivedNodes.setNodes(result.getInt("nodes"));
                archivedNodes.setEntries(result.getFetchSize());

                return archivedNodes;
            }
        });

        model.addAttribute("listArchivedNodesByUser", listArchivedNodes);
        addAdditionalParamsToModel(model);

        return null;
    }

    @RequestMapping("/listNodesByMimeType")
    public String nodesByMimeType(Model model) {

        // Nodes disk space
        String sql = "SELECT sum(content_size) disk_space " +
        			 "FROM (SELECT DISTINCT content_url_id,mimetype_str,content_size " +
        			 "FROM alf_content_data  content, alf_content_url  contentUrl, alf_mimetype  mime " +
        			 "WHERE content.content_mimetype_id = mime.id and " +
        			 "contentUrl.id = content.content_url_id) AS dct";
        
        List < NodesList > diskSpace = jdbcTemplate.query(sql, new RowMapper < NodesList > () {
            public NodesList mapRow(ResultSet result, int rowNum) throws SQLException {
            	NodesList nodesDiskSpace = new NodesList();
            	nodesDiskSpace.setDiskSpace(result.getLong("disk_space"));

                return nodesDiskSpace;
            }
        });
        
        model.addAttribute("totalDiskSpace", diskSpace);
        
        // Nodes by mime type
        sql = "SELECT mimetype_str as mimetype, count(*) occurrencies, sum(content_size) disk_space " +
        			 "FROM (SELECT DISTINCT content_url_id,mimetype_str,content_size " +
        			 "FROM alf_content_data  content, alf_content_url  contentUrl, alf_mimetype  mime " +
        			 "WHERE content.content_mimetype_id = mime.id and " +
        			 "contentUrl.id = content.content_url_id) AS dct " +
        			 "GROUP BY mimetype " +
        			 "ORDER BY occurrencies DESC";
        
        List < NodesList > listNodesByMimeType = jdbcTemplate.query(sql, new RowMapper < NodesList > () {
            public NodesList mapRow(ResultSet result, int rowNum) throws SQLException {
            	NodesList nodesByMimeType = new NodesList();
            	nodesByMimeType.setDiskSpace(result.getLong("disk_space"));
            	nodesByMimeType.setMimeType(result.getString("mimeType"));
            	nodesByMimeType.setOccurrencies(result.getInt("occurrencies"));

                return nodesByMimeType;
            }
        });
        
        model.addAttribute("listNodesByMimeType", listNodesByMimeType);

        addAdditionalParamsToModel(model);

        return null;
    }
    
    @RequestMapping("/listNodesByType")
    public String nodesByType(Model model) {
   
        // Nodes by type
        String sql = "SELECT ('{' || ns.uri || '}' || names.local_name) as node_type, count(*)  as occurrencies " +
        			 "FROM alf_node nodes " +
        			 "JOIN alf_qname names  ON (nodes.type_qname_id = names.id) " +
        			 "JOIN alf_namespace ns ON (names.ns_id = ns.id) " +
        			 "WHERE nodes.type_qname_id=names.id " +
        			 "GROUP BY nodes.type_qname_id, names.local_name, ns.uri " +
        			 "ORDER BY occurrencies DESC";
        
        List < NodesList > listNodesByType = jdbcTemplate.query(sql, new RowMapper < NodesList > () {
            public NodesList mapRow(ResultSet result, int rowNum) throws SQLException {
            	NodesList nodesByMimeType = new NodesList();
            	nodesByMimeType.setNodeType(result.getString("node_type"));
            	nodesByMimeType.setOccurrencies(result.getInt("occurrencies"));

                return nodesByMimeType;
            }
        });
        
        model.addAttribute("listNodesByType", listNodesByType);

        addAdditionalParamsToModel(model);

        return null;
    }
    
    @RequestMapping("/listNodesByStore")
    public String nodesByStore(Model model) {
    	
        // Nodes by store
        String sql = "SELECT (stores.protocol || concat('://' || stores.identifier)) as store, count(*) as occurrencies " +
        			 "FROM alf_node nodes, alf_store stores " +
        			 "WHERE stores.id=nodes.store_id " +
        			 "GROUP BY stores.protocol, stores.identifier ";
        
        List < NodesList > listNodesByStore = jdbcTemplate.query(sql, new RowMapper < NodesList > () {
            public NodesList mapRow(ResultSet result, int rowNum) throws SQLException {
            	NodesList nodesByStore = new NodesList();
            	nodesByStore.setStore(result.getString("store"));
            	nodesByStore.setOccurrencies(result.getInt("occurrencies"));

                return nodesByStore;
            }
        });
        
        model.addAttribute("listNodesByStore", listNodesByStore);

        addAdditionalParamsToModel(model);

        return null;
    }

    @RequestMapping("/lockedResources")
    public String lockedResources(Model model) {
    	
        // Locked resources
        String sql = "select al.id, al.lock_token, start_time, expiry_time, alr1.qname_localname as shared, alr2.qname_localname as exclusive, uri " +
        			 "from alf_lock as al, alf_lock_resource as alr1, alf_lock_resource as alr2, alf_namespace an " +
        			 "where alr1.id = al.shared_resource_id " +
        			 "and alr2.id = al.excl_resource_id " +
        			 "and an.id = alr1.qname_ns_id " +
        			 "order by al.id";
        			 
        List < LockedResources > listLockedResources = jdbcTemplate.query(sql, new RowMapper < LockedResources > () {
            public LockedResources mapRow(ResultSet result, int rowNum) throws SQLException {
            	LockedResources lockedResources = new LockedResources();
            	lockedResources.setId(result.getInt("id"));
            	lockedResources.setLockToken(result.getString("lock_token"));
            	lockedResources.setStartTime(result.getString("start_time"));
            	lockedResources.setExpiryTime(result.getString("expiry_time"));
            	lockedResources.setSharedResource(result.getString("shared"));
            	lockedResources.setExclusiveResource(result.getString("exclusive"));
            	lockedResources.setUri(result.getString("uri"));

                return lockedResources;
            }
        });
        
        model.addAttribute("listLockedResources", listLockedResources);
        
        addAdditionalParamsToModel(model);

        return null;
    }
    
    private void addAdditionalParamsToModel(Model model) {
        // Need this entry for large folders url
        model.addAttribute("largeFolderSize", largeFolderSize);
        // Need this entry for large transactions url
        model.addAttribute("largeTransactionSize", largeTransactionSize);   		
	}
}
