package com.rockvole.logback;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import jline.console.ConsoleReader;
import jline.console.KeyMap;
import jline.console.Operation;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class ShowLogBackViewPort {
    public enum KeyCode {
        KEY_UNKNOWN(1),
        KEY_UP(2),
        KEY_DOWN(3),
        KEY_RIGHT(4),
        KEY_LEFT(5),
        KEY_SPACE(6),
        KEY_PAGE_UP(7),
        KEY_PAGE_DOWN(8),
        KEY_ESC(9);
        private int value;
        private KeyCode(int value) {
            this.value = value;
        }
        public int intValue() { return value; }
    }
    private static final String DB_NAME = "android";
    private static final String DB_USER = "aversions";
    private static final String DB_PASSWORD = "aversions";


    public static void main(String[] args) throws IOException {
        Map<Long, LogbackTableStruct> map;
        KeyCode keyCode=KeyCode.KEY_UNKNOWN;
        Long currentTs=null;
        int screenWidth = jline.TerminalFactory.get().getWidth();
        int screenHeight = jline.TerminalFactory.get().getHeight();
        int linesToShow = (screenHeight / 2);
        do {
            if(currentTs!=null) {
                switch(keyCode) {
                    case KEY_UP:
                        currentTs = getPreviousTs(currentTs,1);
                        break;
                    case KEY_PAGE_UP:
                        currentTs = getPreviousTs(currentTs,linesToShow);
                        break;
                    case KEY_DOWN:
                        currentTs = getNextTs(currentTs, 1);
                        break;
                    case KEY_PAGE_DOWN:
                        currentTs = getNextTs(currentTs, linesToShow);
                        break;
                }
            }
            map = fetchRows(currentTs, linesToShow);
            currentTs = displayRows(map);
            keyCode = readKey();
        } while(keyCode!=KeyCode.KEY_ESC);

    }

    private static Map<Long, LogbackTableStruct> fetchRows(Long currentTs, int linesToShow) {

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
        }
        sql +=       " ORDER BY timestmp desc" +
                     "    LIMIT " + linesToShow;
        System.out.println(sql);

        try {
            Connection conn = fetchDataSource().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            LogbackTableStruct struct;
            while(rs.next()) {
                struct = new LogbackTableStruct(rs.getLong("TIMESTMP"),
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

                map.put(struct.ts, struct);
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return map;
    }

    private static Long getPreviousTs(Long currentTs, int limit) {
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

    private static Long getNextTs(Long currentTs, int limit) {
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

    private static Long displayRows(Map<Long, LogbackTableStruct> map) {
        LogbackTableStruct struct;
        Long lastTs=null;
        for(Map.Entry<Long,LogbackTableStruct> entry : map.entrySet()) {
            struct = entry.getValue();
            lastTs = struct.ts;
            System.out.print(struct.ts+" "+struct.loggerName+" "+struct.levelString+" ");
            System.out.print(struct.threadName+" "+struct.refFlag+" "+struct.arg0+" "+struct.arg1+" "+struct.arg2+" "+struct.arg3+" ");
            System.out.println(struct.fileName+" "+struct.callerClass+" "+struct.method+" "+struct.callerLine+" "+struct.eventId);
            System.out.println(struct.message);
        }
        return lastTs;
    }



    private static KeyCode readKey() {

        try {
            ConsoleReader reader = new ConsoleReader();

            Object bindingObject = null, lastBindingObject = null;
            Character bindingChar, lastBindingChar = null;
            Operation bindingOp = null, lastBindingOp = null;

            bindingOp = null;
            lastBindingOp = null;
            bindingChar = null;
            lastBindingChar = null;
            bindingObject = reader.readBinding(KeyMap.emacs());
            if (bindingObject instanceof Operation) {
                bindingOp = (Operation) bindingObject;
            } else {
                bindingChar = ((String) bindingObject).charAt(0);
            }
            if(bindingOp==Operation.COMPLETE) return KeyCode.KEY_ESC;

            lastBindingObject = reader.getLastBinding();
            if (lastBindingObject instanceof Operation) {
                lastBindingOp = (Operation) lastBindingObject;
            } else {
                lastBindingChar = ((String) lastBindingObject).charAt(0);
            }
            switch (bindingOp) {
                case SELF_INSERT:
                    switch (lastBindingChar) {
                        case 'A':
                            return KeyCode.KEY_UP;
                        case 'C':
                            return KeyCode.KEY_RIGHT;
                        case 'D':
                            return KeyCode.KEY_LEFT;
                        case 'B':
                            return KeyCode.KEY_DOWN;
                        case ' ':
                            return KeyCode.KEY_SPACE;
                        case '5':
                            return KeyCode.KEY_PAGE_UP;
                        case '6':
                            return KeyCode.KEY_PAGE_DOWN;
                        default:
                    }
                    break;
                default:
                    System.err.println("Unknown binding");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return KeyCode.KEY_UNKNOWN;
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