package com.rockvole.logback;

import java.util.Map;

public class DisplayTable {
    static String SEP="|";
    static String SPC = " "+SEP+" ";
    static int SPC_WIDTH = 3;

    public static Long displayRows(Map<Long, LogbackTableStruct> map, ConfigurationStruct configurationStruct) {

        LogbackTableStruct struct;
        Long lastTs=null;

        for(Map.Entry<Long,LogbackTableStruct> entry : map.entrySet()) {
            struct = entry.getValue();
            lastTs = struct.ts;
            System.out.print(SEP+" "+displayField(struct.ts, configurationStruct.ts));
            System.out.print(displayField(struct.loggerName, configurationStruct.loggerName));
            System.out.print(displayField(struct.levelString, configurationStruct.levelString));
            System.out.print(displayField(struct.threadName, configurationStruct.threadName));
            System.out.print(displayField(struct.refFlag, configurationStruct.refFlag));
            System.out.print(displayField(struct.arg0, configurationStruct.arg0));
            System.out.print(displayField(struct.arg1, configurationStruct.arg1));
            System.out.print(displayField(struct.arg2, configurationStruct.arg2));
            System.out.print(displayField(struct.arg3, configurationStruct.arg3));
            System.out.print(displayField(struct.fileName, configurationStruct.fileName));
            System.out.print(displayField(struct.callerClass, configurationStruct.callerClass));
            System.out.print(displayField(struct.method, configurationStruct.method));
            System.out.print(displayField(struct.callerLine, configurationStruct.callerLine));
            System.out.print(displayField(struct.eventId, configurationStruct.eventId));
            System.out.println(displayField(struct.message, configurationStruct.message));
        }
        return lastTs;
    }

    private static String displayField(Object field, FieldStruct struct) {
        if(!struct.show) return "";
        if(field==null) return ""+SPC;
        return field.toString()+SPC;
    }

    public static ConfigurationStruct calculateWidths(ConfigurationStruct struct, int screenWidth) {
        int minWidth = struct.ts.minWidth + struct.loggerName.minWidth + struct.levelString.minWidth +
                struct.threadName.minWidth + struct.refFlag.minWidth + struct.arg0.minWidth +
                struct.arg1.minWidth + struct.arg2.minWidth + struct.arg3.minWidth +
                struct.fileName.minWidth + struct.callerClass.minWidth + struct.method.minWidth +
                struct.callerLine.minWidth + struct.eventId.minWidth;
        int fieldsToDisplay = 0;
        if(struct.ts.show) fieldsToDisplay++;
        if(struct.loggerName.show) fieldsToDisplay++;
        if(struct.levelString.show) fieldsToDisplay++;
        if(struct.threadName.show) fieldsToDisplay++;
        if(struct.refFlag.show) fieldsToDisplay++;
        if(struct.arg0.show) fieldsToDisplay++;
        if(struct.arg1.show) fieldsToDisplay++;
        if(struct.arg2.show) fieldsToDisplay++;
        if(struct.arg3.show) fieldsToDisplay++;
        if(struct.fileName.show) fieldsToDisplay++;
        if(struct.callerClass.show) fieldsToDisplay++;
        if(struct.method.show) fieldsToDisplay++;
        if(struct.callerLine.show) fieldsToDisplay++;
        if(struct.eventId.show) fieldsToDisplay++;

        int remainingWidth = (screenWidth - minWidth);
        if(remainingWidth > 0) {
            int columnExtraWidth = (remainingWidth / fieldsToDisplay);
            if(columnExtraWidth > 0) {
                if(struct.ts.show) struct.ts.minWidth += columnExtraWidth;
                if(struct.loggerName.show) struct.loggerName.minWidth += columnExtraWidth;
                if(struct.levelString.show) struct.levelString.minWidth += columnExtraWidth;
                if(struct.threadName.show) struct.threadName.minWidth += columnExtraWidth;
                if(struct.refFlag.show) struct.refFlag.minWidth += columnExtraWidth;
                if(struct.arg0.show) struct.arg0.minWidth += columnExtraWidth;
                if(struct.arg1.show) struct.arg1.minWidth += columnExtraWidth;
                if(struct.arg2.show) struct.arg2.minWidth += columnExtraWidth;
                if(struct.arg3.show) struct.arg3.minWidth += columnExtraWidth;
                if(struct.fileName.show) struct.fileName.minWidth += columnExtraWidth;
                if(struct.callerClass.show) struct.callerClass.minWidth += columnExtraWidth;
                if(struct.method.show) struct.method.minWidth += columnExtraWidth;
                if(struct.callerLine.show) struct.callerLine.minWidth += columnExtraWidth;
                if(struct.eventId.show) struct.eventId.minWidth += columnExtraWidth;
            }
        }
        return struct;
    }

}
