package com.rockvole.logback.tools;

import com.rockvole.logback.data.ConfigurationStruct;
import com.rockvole.logback.data.FieldStruct;
import com.rockvole.logback.data.LogbackTableStruct;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public class DisplayTable {
    static public String SEP="|";
    static public String COR="+";
    static public String SPC = " "+SEP+" ";
    static public int SPC_WIDTH = 3;

    public static Long displayRows(Map<Long, LogbackTableStruct> map, ConfigurationStruct configurationStruct) {
        SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
        Date date=null;
        LogbackTableStruct struct;
        Long lastTs=null;
        displayRow(configurationStruct.ts.name, configurationStruct.loggerName.name, configurationStruct.levelString.name,
                configurationStruct.threadName.name, configurationStruct.refFlag.name, configurationStruct.arg0.name, configurationStruct.arg1.name,
                configurationStruct.arg2.name, configurationStruct.arg3.name, configurationStruct.fileName.name, configurationStruct.callerClass.name,
                configurationStruct.method.name, configurationStruct.callerLine.name, configurationStruct.eventId.name,
                getStringOfSize('-', configurationStruct.message.minWidth), configurationStruct);
        for(Map.Entry<Long,LogbackTableStruct> entry : map.entrySet()) {
            struct = entry.getValue();
            lastTs = struct.ts;
            date = new Date(struct.ts);
            displayRow(ft.format(date), struct.loggerName, struct.levelString, struct.threadName, struct.refFlag.toString(), struct.arg0,
                       struct.arg1, struct.arg2, struct.arg3, struct.fileName, struct.callerClass, struct.method, struct.callerLine,
                       struct.eventId.toString(), struct.message, configurationStruct);
        }
        return lastTs;
    }

    private static void displayRow(String ts, String loggerName, String levelString, String threadName, String refFlag,
                                   String arg0, String arg1, String arg2, String arg3, String fileName, String callerClass,
                                   String method, String callerLine, String eventId, String message, ConfigurationStruct struct) {
        System.out.print(SEP + " " + displayField(ts, struct.ts, SPC));
        System.out.print(displayField(loggerName, struct.loggerName, SPC));
        System.out.print(displayField(levelString, struct.levelString, SPC));
        System.out.print(displayField(threadName, struct.threadName, SPC));
        System.out.print(displayField(refFlag, struct.refFlag, SPC));
        System.out.print(displayField(arg0, struct.arg0, SPC));
        System.out.print(displayField(arg1, struct.arg1, SPC));
        System.out.print(displayField(arg2, struct.arg2, SPC));
        System.out.print(displayField(arg3, struct.arg3, SPC));
        System.out.print(displayField(fileName, struct.fileName, SPC));
        System.out.print(displayField(callerClass, struct.callerClass, SPC));
        System.out.print(displayField(method, struct.method, SPC));
        System.out.print(displayField(callerLine, struct.callerLine, SPC));
        System.out.println(displayField(eventId, struct.eventId, SPC));
        System.out.println(COR + " " + displayField(message, struct.message, " " + COR));

    }

    private static String displayField(String field, FieldStruct struct, String endMark) {
        if(!struct.show) return "";
        if(field==null) field="";
        field = field.replaceAll("\\p{Cntrl}","~");
        int widthNoPadding=(struct.minWidth-endMark.length());

        int padding = widthNoPadding-field.length();
        //System.err.println(struct.name + "|val='" + field + "' padding=" + padding + "||mw=" + (struct.minWidth-SPC_WIDTH)+"||ln="+field.length());
        if(padding<0) return field.substring(0, widthNoPadding)+endMark;
        return field+getStringOfSize(' ', padding)+endMark;
    }

    private static String toHex(String arg) {
        return String.format("%040x", new BigInteger(1, arg.getBytes()));
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
        struct.message.minWidth = totalWidth-1;
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
