package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.ActivitiesFeed;
import com.alfresco.support.alfrescodb.model.LargeTransaction;
import com.alfresco.support.alfrescodb.model.Workflow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ActivitiesFeedMapper {
    @Select("select count(*) as occurrences, to_char(post_date, 'YYYY-Mon-DD') as date, site_network as siteNetowkr, activity_type as activityType " +
            "from alf_activity_feed " +
            "where feed_user_id != '@@NULL@@' " +
            "and feed_user_id = post_user_id " +
            "group by date, site_network, activity_type") //SQL
    List<ActivitiesFeed> findActivitiesByActivityType();

    @Select("select count(*) as occurrences, to_char(post_date, 'YYYY-Mon-DD') as date, site_network as siteNetwork, feed_user_id as feedUserId " +
            "from alf_activity_feed " +
            "where feed_user_id != '@@NULL@@' " +
            "and feed_user_id = post_user_id " +
            "group by date, site_network, feed_user_id") //SQL
    List<ActivitiesFeed> findActivitiesByUser();

    @Select("select count(*) as occurrences, to_char(post_date, 'YYYY-Mon-DD') as date, site_network as siteNetwork, app_tool as appTool " +
            "from alf_activity_feed " +
            "where feed_user_id != '@@NULL@@' " +
            "and feed_user_id = post_user_id " +
            "group by date, site_network, app_tool") //SQL
    List<ActivitiesFeed> findActivitiesByApplicationInterface();
}