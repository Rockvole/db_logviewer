package com.rockvole.logback;

import java.util.Arrays;
import java.util.Map;

public class DisplayTable {
    static String SEP="|";
    static String COR="+";
    static String SPC = " "+SEP+" ";
    static int SPC_WIDTH = 3;

    public static Long displayRows(Map<Long, LogbackTableStruct> map, ConfigurationStruct configurationStruct) {

        LogbackTableStruct struct;
        Long lastTs=null;

        for(Map.Entry<Long,LogbackTableStruct> entry : map.entrySet()) {
            struct = entry.getValue();
            lastTs = struct.ts;
            System.out.print(SEP + " " + displayField(struct.ts, configurationStruct.ts, SPC));
            System.out.print(displayField(struct.loggerName, configurationStruct.loggerName, SPC));
            System.out.print(displayField(struct.levelString, configurationStruct.levelString, SPC));
            System.out.print(displayField(struct.threadName, configurationStruct.threadName, SPC));
            System.out.print(displayField(struct.refFlag, configurationStruct.refFlag, SPC));
            System.out.print(displayField(struct.arg0, configurationStruct.arg0, SPC));
            System.out.print(displayField(struct.arg1, configurationStruct.arg1, SPC));
            System.out.print(displayField(struct.arg2, configurationStruct.arg2, SPC));
            System.out.print(displayField(struct.arg3, configurationStruct.arg3, SPC));
            System.out.print(displayField(struct.fileName, configurationStruct.fileName, SPC));
            System.out.print(displayField(struct.callerClass, configurationStruct.callerClass, SPC));
            System.out.print(displayField(struct.method, configurationStruct.method, SPC));
            System.out.print(displayField(struct.callerLine, configurationStruct.callerLine, SPC));
            System.out.println(displayField(struct.eventId, configurationStruct.eventId, SPC));
            System.out.println(COR + " " + displayField(struct.message, configurationStruct.message, COR));
        }
        return lastTs;
    }

    private static String displayField(Object field, FieldStruct struct, String endMark) {
        if(!struct.show) return "";
        if(field==null) field="";
        int widthNoPadding=(struct.minWidth-SPC_WIDTH);

        int padding = widthNoPadding-field.toString().length();
        //System.err.println(struct.name+"|val='"+field+"' padding="+padding+"||mw="+(struct.minWidth-SPC_WIDTH)+"||ln="+field.toString().length());
        if(padding<0) return field.toString().substring(0, widthNoPadding)+endMark;
        return field.toString()+getStringOfSize(' ', padding)+endMark;
    }

    public static ConfigurationStruct calculateWidths(ConfigurationStruct struct, int screenWidth) {
        /*
        System.err.println(struct.ts);
        System.err.println(struct.loggerName);
        System.err.println(struct.levelString);
        System.err.println(struct.threadName);
        System.err.println(struct.refFlag);
        System.err.println(struct.arg0);
        System.err.println(struct.arg1);
        System.err.println(struct.arg2);
        System.err.println(struct.arg3);
        System.err.println(struct.fileName);
        System.err.println(struct.callerClass);
        System.err.println(struct.method);
        System.err.println(struct.callerLine);
        System.err.println(struct.eventId);
        */
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
        //System.err.println("ftdisp=" + fieldsToDisplay + "||sw=" + screenWidth + "||mw=" + minWidth + "||rw=" + remainingWidth);
        int totalWidth=minWidth;
        if(remainingWidth > 0) {
            int columnExtraWidth = (remainingWidth / fieldsToDisplay);
            System.err.println("cew=" + columnExtraWidth);
            if(columnExtraWidth > 0) {
                if(struct.ts.show) {
                    struct.ts.minWidth += columnExtraWidth;
                    totalWidth += columnExtraWidth;
                }
                if(struct.loggerName.show) {
                    struct.loggerName.minWidth += columnExtraWidth;
                    totalWidth += columnExtraWidth;
                }
                if(struct.levelString.show) {
                    struct.levelString.minWidth += columnExtraWidth;
                    totalWidth += columnExtraWidth;
                }
                if(struct.threadName.show) {
                    struct.threadName.minWidth += columnExtraWidth;
                    totalWidth += columnExtraWidth;
                }
                if(struct.refFlag.show) {
                    struct.refFlag.minWidth += columnExtraWidth;
                    totalWidth += columnExtraWidth;
                }
                if(struct.arg0.show) {
                    struct.arg0.minWidth += columnExtraWidth;
                    totalWidth += columnExtraWidth;
                }
                if(struct.arg1.show) {
                    struct.arg1.minWidth += columnExtraWidth;
                    totalWidth += columnExtraWidth;
                }
                if(struct.arg2.show) {
                    struct.arg2.minWidth += columnExtraWidth;
                    totalWidth += columnExtraWidth;
                }
                if(struct.arg3.show) {
                    struct.arg3.minWidth += columnExtraWidth;
                    totalWidth += columnExtraWidth;
                }
                if(struct.fileName.show) {
                    struct.fileName.minWidth += columnExtraWidth;
                    totalWidth += columnExtraWidth;
                }
                if(struct.callerClass.show) {
                    struct.callerClass.minWidth += columnExtraWidth;
                    totalWidth += columnExtraWidth;
                }
                if(struct.method.show) {
                    struct.method.minWidth += columnExtraWidth;
                    totalWidth += columnExtraWidth;
                }
                if(struct.callerLine.show) {
                    struct.callerLine.minWidth += columnExtraWidth;
                    totalWidth += columnExtraWidth;
                }
                if(struct.eventId.show) {
                    struct.eventId.minWidth += columnExtraWidth;
                    totalWidth += columnExtraWidth;
                }
            }
        }
        struct.message.minWidth = totalWidth+1;
        /*
        System.err.println(struct.ts);
        System.err.println(struct.loggerName);
        System.err.println(struct.levelString);
        System.err.println(struct.threadName);
        System.err.println(struct.refFlag);
        System.err.println(struct.arg0);
        System.err.println(struct.arg1);
        System.err.println(struct.arg2);
        System.err.println(struct.arg3);
        System.err.println(struct.fileName);
        System.err.println(struct.callerClass);
        System.err.println(struct.method);
        System.err.println(struct.callerLine);
        System.err.println(struct.eventId);
        System.err.println(">>"+struct.message);
        */
        return struct;
    }


    public static String getStringOfSize(int size) {
        return getStringOfSize('Z', size);
    }

    public static String getStringOfSize(char ch, int size) {
        char[] charArray = new char[size];
        Arrays.fill(charArray, ch);
        String str = new String(charArray);
        return str;
    }
}
