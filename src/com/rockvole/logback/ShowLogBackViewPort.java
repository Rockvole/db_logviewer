package com.rockvole.logback;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.rockvole.logback.data.ConfigurationStruct;
import com.rockvole.logback.data.FieldStruct;
import com.rockvole.logback.data.LogbackTableStruct;
import com.rockvole.logback.tools.DataBaseAccess;
import com.rockvole.logback.tools.DisplayTable;
import com.rockvole.logback.tools.KeyboardTools;
import com.rockvole.logback.tools.PropertyFileTools;
import jline.console.ConsoleReader;
import jline.console.KeyMap;
import jline.console.Operation;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.Map;
import java.util.TreeMap;

public class ShowLogBackViewPort {

    public static void main(String[] args) throws IOException {
        Map<Long, LogbackTableStruct> map;
        KeyboardTools.KeyCode keyCode=KeyboardTools.KeyCode.KEY_UNKNOWN;
        Long currentTs=null;
        int screenWidth = jline.TerminalFactory.get().getWidth();
        int screenHeight = jline.TerminalFactory.get().getHeight();
        int linesToShow = (screenHeight / 2);
        ConfigurationStruct configurationStruct = PropertyFileTools.fetchProperties(null);
        do {
            if(currentTs!=null) {
                switch(keyCode) {
                    case KEY_UP:
                        currentTs = DataBaseAccess.getPreviousTs(currentTs, 1);
                        break;
                    case KEY_PAGE_UP:
                        currentTs = DataBaseAccess.getPreviousTs(currentTs,linesToShow);
                        break;
                    case KEY_DOWN:
                        currentTs = DataBaseAccess.getNextTs(currentTs, 1);
                        break;
                    case KEY_PAGE_DOWN:
                        currentTs = DataBaseAccess.getNextTs(currentTs, linesToShow);
                        break;
                }
            }
            configurationStruct = DisplayTable.calculateWidths(configurationStruct, screenWidth);
            map = DataBaseAccess.fetchRows(currentTs, linesToShow);
            currentTs = DisplayTable.displayRows(map, configurationStruct);
            keyCode = KeyboardTools.readKey();
        } while(keyCode!=KeyboardTools.KeyCode.KEY_ESC);

    }

}
