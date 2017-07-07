package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.LargeTransaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LargeTransactionMapper {
    @Select("SELECT trx.id as trxId, count(*) as nodes " +
            "FROM alf_transaction trx, alf_node an " +
            "WHERE an.transaction_id = trx.id " +
            "GROUP BY trx.id " +
            "HAVING count(*) > #{size} " +
            "ORDER BY nodes desc")
    List<LargeTransaction> findBySize(@Param("size") int size);
}