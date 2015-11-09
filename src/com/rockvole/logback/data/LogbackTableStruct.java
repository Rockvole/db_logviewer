package com.rockvole.logback.data;


public class LogbackTableStruct {
    public Long ts;
    public String message;
    public String loggerName;
    public String levelString;
    public String threadName;
    public Short refFlag;
    public String arg0;
    public String arg1;
    public String arg2;
    public String arg3;
    public String fileName;
    public String callerClass;
    public String method;
    public String callerLine;
    public Integer eventId;

    public LogbackTableStruct(Long ts, String message, String loggerName, String levelString, String threadName, Short refFlag,
                              String arg0, String arg1, String arg2, String arg3, String fileName, String callerClass,
                              String method, String callerLine, Integer eventId) {
        this.ts = ts;
        this.message = message;
        this.loggerName = loggerName;
        this.levelString = levelString;
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
