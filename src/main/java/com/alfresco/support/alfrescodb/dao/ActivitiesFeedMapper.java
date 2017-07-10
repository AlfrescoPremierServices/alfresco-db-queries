package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.ActivitiesFeed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ActivitiesFeedMapper {
    @Select("select count(*) as occurrences, to_char(post_date, 'YYYY-Mon-DD') as date, site_network as siteNetwork, activity_type as activityType " +
            "from alf_activity_feed " +
            "where feed_user_id != '@@NULL@@' " +
            "and feed_user_id = post_user_id " +
            "group by date, site_network, activity_type")
    List<ActivitiesFeed> findActivitiesByActivityTypePostgres();

    @Select("select count(*) as occurrences, to_char(post_date, 'YYYY-Mon-DD') as date, site_network as siteNetwork, feed_user_id as feedUserId " +
            "from alf_activity_feed " +
            "where feed_user_id != '@@NULL@@' " +
            "and feed_user_id = post_user_id " +
            "group by date, site_network, feed_user_id")
    List<ActivitiesFeed> findActivitiesByUserPostgres();

    @Select("select count(*) as occurrences, to_char(post_date, 'YYYY-Mon-DD') as date, site_network as siteNetwork, app_tool as appTool " +
            "from alf_activity_feed " +
            "where feed_user_id != '@@NULL@@' " +
            "and feed_user_id = post_user_id " +
            "group by date, site_network, app_tool")
    List<ActivitiesFeed> findActivitiesByApplicationInterfacePostgres();

    @Select("select count(*) as occurrences, substring(post_date, 1, 10) as date, site_network as siteNetowkr, activity_type as activityType " +
            "from alf_activity_feed " +
            "where feed_user_id != '@@NULL@@' " +
            "and feed_user_id = post_user_id " +
            "group by date, site_network, activity_type")
    List<ActivitiesFeed> findActivitiesByActivityTypeMySQL();

    @Select("select count(*) as occurrences, substring(post_date, 1, 10) as date, site_network as siteNetwork, feed_user_id as feedUserId " +
            "from alf_activity_feed " +
            "where feed_user_id != '@@NULL@@' " +
            "and feed_user_id = post_user_id " +
            "group by date, site_network, feed_user_id")
    List<ActivitiesFeed> findActivitiesByUserMySQL();

    @Select("select count(*) as occurrences, substring(post_date, 1, 10) as date, site_network as siteNetwork, app_tool as appTool " +
            "from alf_activity_feed " +
            "where feed_user_id != '@@NULL@@' " +
            "and feed_user_id = post_user_id " +
            "group by date, site_network, app_tool")
    List<ActivitiesFeed> findActivitiesByApplicationInterfaceMySQL();
}