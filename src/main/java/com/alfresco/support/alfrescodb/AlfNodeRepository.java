package com.alfresco.support.alfrescodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.alfresco.support.alfrescodb.AlfNode;

@Repository
public class AlfNodeRepository {
	
    @Autowired
    protected JdbcTemplate jdbc;
    
    private final Logger log = LoggerFactory.getLogger(AlfNodeRepository.class);

    public List<AlfNode> getNodes() throws Exception {
		// TODO Auto-generated method stub
        log.info("Querying for large folders");

        if (jdbc == null){
        	log.info("crap!!!");
        }
        	
        String sql = "SELECT id, uuid, audit_modifier, audit_modified from alf_node where id > 100";
        List<AlfNode> listAlfNode = jdbc.query(sql, new RowMapper<AlfNode>() {
            public AlfNode mapRow(ResultSet result, int rowNum) throws SQLException {
            	AlfNode alf_node = new AlfNode();
                alf_node.setId(result.getLong("id"));
                alf_node.setUuid(result.getString("uuid"));
                alf_node.setAuditModifier(result.getString("audit_modifier"));
                alf_node.setAuditModified(result.getString("audit_modified"));
                 
                return alf_node;
            }
        });
        
        for (AlfNode alf_node : listAlfNode) {
            System.out.println(alf_node);
        }
		return listAlfNode;	
	}
}