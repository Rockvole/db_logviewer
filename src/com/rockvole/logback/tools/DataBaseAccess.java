package com.rockvole.logback.tools;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.rockvole.logback.data.ConfigurationStruct;
import com.rockvole.logback.data.LogbackTableStruct;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class DataBaseAccess {
    private static final String DB_NAME = "android";
    private static final String DB_USER = "aversions";
    private static final String DB_PASSWORD = "aversions";

    public static Map<Long, LogbackTableStruct> fetchRows(Long currentTs, int linesToShow, ConfigurationStruct struct) {

        Map<Long, LogbackTableStruct> map = new TreeMap<Long,LogbackTableStruct>();
        String sql = "   SELECT timestmp, " +
                "          formatted_message, " +
                "          logger_name, " +
                "          level_string, " +
                "          thread_name, " +
                "          reference_flag, " +
                "          arg0, " +
                "          arg1, " +
                "          arg2, " +
                "          arg3, " +
                "          caller_filename, " +
                "          caller_class, " +
                "          caller_method, " +
                "          caller_line, " +
                "          event_id " +
                "     FROM logging_event ";
        if(currentTs!=null) {
            sql +=   "    WHERE timestmp <= " + currentTs;
        } else {
            sql +=   "    WHERE 1=1 ";
        }
        if(struct.loggerName.like !=null) {
            sql += "      AND logger_name LIKE '"+struct.loggerName.like +"'";
        }
        if(struct.loggerName.notLike !=null) {
            sql += "      AND logger_name NOT LIKE '"+struct.loggerName.notLike +"'";
        }
        if(struct.levelString.like !=null) {
            sql += "      AND level_string LIKE '"+struct.levelString.like +"'";
        }
        if(struct.levelString.notLike !=null) {
            sql += "      AND level_string NOT LIKE '"+struct.levelString.notLike +"'";
        }
        if(struct.threadName.like !=null) {
            sql += "      AND thread_name LIKE '"+struct.threadName.like +"'";
        }
        if(struct.threadName.notLike !=null) {
            sql += "      AND thread_name NOT LIKE '"+struct.threadName.notLike +"'";
        }
        if(struct.fileName.like !=null) {
            sql += "      AND caller_filename LIKE '"+struct.fileName.like +"'";
        }
        if(struct.fileName.notLike !=null) {
            sql += "      AND caller_filename NOT LIKE '"+struct.fileName.notLike +"'";
        }
        if(struct.callerClass.like !=null) {
            sql += "      AND caller_class LIKE '"+struct.callerClass.like +"'";
        }
        if(struct.callerClass.notLike !=null) {
            sql += "      AND caller_class NOT LIKE '"+struct.callerClass.notLike +"'";
        }
        if(struct.method.like !=null) {
            sql += "      AND caller_method LIKE '"+struct.method.like +"'";
        }
        if(struct.method.notLike !=null) {
            sql += "      AND caller_method NOT LIKE '"+struct.method.notLike +"'";
        }
        if(struct.callerLine.like !=null) {
            sql += "      AND caller_line LIKE '"+struct.callerLine.like +"'";
        }
        if(struct.callerLine.notLike !=null) {
            sql += "      AND caller_line NOT LIKE '"+struct.callerLine.notLike +"'";
        }
        if(struct.eventId.like !=null) {
            sql += "      AND event_id LIKE '"+struct.eventId.like +"'";
        }
        if(struct.eventId.notLike !=null) {
            sql += "      AND event_id NOT LIKE '"+struct.eventId.notLike +"'";
        }
        sql +=       " ORDER BY timestmp desc" +
                "    LIMIT " + linesToShow;
        //System.out.println(sql);
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            conn = fetchDataSource().getConnection();
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();
            LogbackTableStruct tableStruct;
            while(rs.next()) {
                tableStruct = new LogbackTableStruct(rs.getLong("TIMESTMP"),
                        rs.getString("FORMATTED_MESSAGE"),
                        rs.getString("LOGGER_NAME"),
                        rs.getString("LEVEL_STRING"),
                        rs.getString("THREAD_NAME"),
                        rs.getShort("REFERENCE_FLAG"),
                        rs.getString("ARG0"),
                        rs.getString("ARG1"),
                        rs.getString("ARG2"),
                        rs.getString("ARG3"),
                        rs.getString("CALLER_FILENAME"),
                        rs.getString("CALLER_CLASS"),
                        rs.getString("CALLER_METHOD"),
                        rs.getString("CALLER_LINE"),
                        rs.getInt("EVENT_ID"));

                map.put(tableStruct.ts, tableStruct);
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if(ps!=null) ps.close();
                if(conn!=null) conn.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static Long getPreviousTs(Long currentTs, int limit) {
        Long previousTs=null;
        String sql = "   SELECT timestmp " +
                "     FROM logging_event " +
                "    WHERE timestmp < " + currentTs +
                " ORDER BY timestmp desc" +
                "    LIMIT "+limit;
        try {
            Connection conn = fetchDataSource().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                previousTs = rs.getLong("TIMESTMP");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return previousTs;
    }

    public static Long getNextTs(Long currentTs, int limit) {
        Long nextTs=null;
        String sql = "   SELECT timestmp " +
                "     FROM logging_event " +
                "    WHERE timestmp > " + currentTs +
                " ORDER BY timestmp asc" +
                "    LIMIT "+limit;
        try {
            Connection conn = fetchDataSource().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                nextTs = rs.getLong("TIMESTMP");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return nextTs;
    }

    private static DataSource fetchDataSource() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName("localhost");
        ds.setPortNumber(3306);
        ds.setDatabaseName(DB_NAME);
        ds.setUser(DB_USER);
        ds.setPassword(DB_PASSWORD);
        return ds;
    }
}
