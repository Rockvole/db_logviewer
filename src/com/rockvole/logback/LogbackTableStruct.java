package com.rockvole.logback;


public class LogbackTableStruct {
    Long ts;
    String message;
    String loggerName;
    String levelString;
    String threadName;
    Short refFlag;
    String arg0;
    String arg1;
    String arg2;
    String arg3;
    String fileName;
    String callerClass;
    String method;
    String callerLine;
    Integer eventId;

    public LogbackTableStruct(Long ts, String message, String loggerName, String levelString, String threadName, Short refFlag,
                              String arg0, String arg1, String arg2, String arg3, String fileName, String callerClass,
                              String method, String callerLine, Integer eventId) {
        this.ts = ts;
        this.message = message;
        this.loggerName = loggerName;
        this.threadName = threadName;
        this.refFlag = refFlag;
        this.arg0 = arg0;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
        this.fileName = fileName;
        this.callerClass = callerClass;
        this.method = method;
        this.callerLine = callerLine;
        this.eventId = eventId;
    }
}
