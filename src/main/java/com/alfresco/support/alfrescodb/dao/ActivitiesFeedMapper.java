package com.alfresco.support.alfrescodb.dao;

import com.alfresco.support.alfrescodb.model.ActivitiesFeed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ActivitiesFeedMapper {
    @Select("select count(*) as occurrences, to_char(post_date, 'YYYY-Mon-DD') as postDate, site_network as siteNetwork, activity_type as activityType " +
            "from alf_activity_feed " +
            "where feed_user_id != '@@NULL@@' " +
            "and feed_user_id = post_user_id " +
            "group by postDate, site_network, activity_type")
    List<ActivitiesFeed> findActivitiesByActivityTypePostgres();

    @Select("select count(*) as occurrences, to_char(post_date, 'YYYY-Mon-DD') as postDate, site_network as siteNetwork, feed_user_id as feedUserId " +
            "from alf_activity_feed " +
            "where feed_user_id != '@@NULL@@' " +
            "and feed_user_id = post_user_id " +
            "group by postDate, site_network, feed_user_id")
    List<ActivitiesFeed> findActivitiesByUserPostgres();

    @Select("select count(*) as occurrences, to_char(post_date, 'YYYY-Mon-DD') as postDate, site_network as siteNetwork, app_tool as appTool " +
            "from alf_activity_feed " +
            "where feed_user_id != '@@NULL@@' " +
            "and feed_user_id = post_user_id " +
            "group by postDate, site_network, app_tool")
    List<ActivitiesFeed> findActivitiesByApplicationInterfacePostgres();

    @Select("select count(*) as occurrences, substring(post_date, 1, 11) as postDate, site_network as siteNetwork, activity_type as activityType " +
            "from alf_activity_feed " +
            "where feed_user_id != '@@NULL@@' " +
            "and feed_user_id = post_user_id " +
            "group by post_date, site_network, activity_type")
    List<ActivitiesFeed> findActivitiesByActivityTypeMySQL();

    @Select("select count(*) as occurrences, substring(post_date, 1, 11) as postDate, site_network as siteNetwork, feed_user_id as feedUserId " +
            "from alf_activity_feed " +
            "where feed_user_id != '@@NULL@@' " +
            "and feed_user_id = post_user_id " +
            "group by post_date, site_network, feed_user_id")
    List<ActivitiesFeed> findActivitiesByUserMySQL();

    @Select("select count(*) as occurrences, substring(post_date, 1, 11) as postDate, site_network as siteNetwork, app_tool as appTool " +
            "from alf_activity_feed " +
            "where feed_user_id != '@@NULL@@' " +
            "and feed_user_id = post_user_id " +
            "group by post_date, site_network, app_tool")
    List<ActivitiesFeed> findActivitiesByApplicationInterfaceMySQL();

    @Select("select count(*) as occurrences, substr(post_date, 1, 10) as postDate, site_network as siteNetwork, activity_type as activityType\n" +
            "from alf_activity_feed\n" +
            "where feed_user_id != '@@NULL@@'\n" +
            "and feed_user_id = post_user_id\n" +
            "group by post_date, site_network, activity_type\n")
    List<ActivitiesFeed> findActivitiesByActivityTypeOracle();

    @Select("select count(*) as occurrences, substr(post_date, 1, 10) as postDate, site_network as siteNetwork, feed_user_id as feedUserId\n" +
            "from alf_activity_feed\n" +
            "where feed_user_id != '@@NULL@@'\n" +
            "and feed_user_id = post_user_id\n" +
            "group by post_date, site_network, feed_user_id")
    List<ActivitiesFeed> findActivitiesByUserOracle();

    @Select("select count(*) as occurrences, substr(post_date, 1, 10) as postDate, site_network as siteNetwork, app_tool as appTool\n" +
            "from alf_activity_feed\n" +
            "where feed_user_id != '@@NULL@@'\n" +
            "and feed_user_id = post_user_id\n" +
            "group by post_date, site_network, app_tool")
    List<ActivitiesFeed> findActivitiesByApplicationInterfaceOracle();

    @Select("select count(*) as occurrences, substring(CONVERT(VARCHAR(11), post_date), 1, 11) as postDate, " +
            "site_network as siteNetwork, activity_type as activityType\n" +
            "from alf_activity_feed\n" +
            "where feed_user_id != '@@NULL@@'\n" +
            "and feed_user_id = post_user_id\n" +
            "group by post_date, site_network, activity_type\n")
    List<ActivitiesFeed> findActivitiesByActivityTypeMSSql();

    @Select("select count(*) as occurrences, substring(CONVERT(VARCHAR(11), post_date), 1, 11) as postDate, " +
            "site_network as siteNetwork, feed_user_id as feedUserId\n" +
            "from alf_activity_feed\n" +
            "where feed_user_id != '@@NULL@@'\n" +
            "and feed_user_id = post_user_id\n" +
            "group by post_date, site_network, feed_user_id")
    List<ActivitiesFeed> findActivitiesByUserMSSql();

    @Select("select count(*) as occurrences, substring(CONVERT(VARCHAR(11), post_date), 1, 11) as postDate, " +
            "site_network as siteNetwork, app_tool as appTool\n" +
            "from alf_activity_feed\n" +
            "where feed_user_id != '@@NULL@@'\n" +
            "and feed_user_id = post_user_id\n" +
            "group by post_date, site_network, app_tool")
    List<ActivitiesFeed> findActivitiesByApplicationInterfaceMSSql();
}
