package com.rockvole.logback;

import com.rockvole.logback.data.ConfigurationStruct;
import com.rockvole.logback.data.LogbackTableStruct;
import com.rockvole.logback.tools.DataBaseAccess;
import com.rockvole.logback.tools.DisplayTable;
import com.rockvole.logback.tools.KeyboardTools;
import com.rockvole.logback.tools.PropertyFileTools;

import java.io.IOException;
import java.util.Map;

public class ShowLogBackViewPort {

    public static void main(String[] args) throws IOException {
        Map<Long, LogbackTableStruct> map;
        KeyboardTools.KeyCode keyCode=KeyboardTools.KeyCode.KEY_UNKNOWN;
        Long currentTs=null;
        int screenWidth = jline.TerminalFactory.get().getWidth();
        int screenHeight = jline.TerminalFactory.get().getHeight();
        boolean even = (screenHeight % 2) == 0;
        int linesToShow = ((screenHeight-1) / 2) - 1; // -1 for header & -1 for cursor
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
            map = DataBaseAccess.fetchRows(currentTs, linesToShow, configurationStruct);
            if(even) System.out.println(""); // Add blank line
            currentTs = DisplayTable.displayRows(map, configurationStruct);
            keyCode = KeyboardTools.readKey();
        } while(keyCode!=KeyboardTools.KeyCode.KEY_ESC);

    }

}
