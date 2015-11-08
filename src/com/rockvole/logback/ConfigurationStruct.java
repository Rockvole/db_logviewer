package com.rockvole.logback;

public class ConfigurationStruct {
    FieldStruct ts;
    FieldStruct message;
    FieldStruct loggerName;
    FieldStruct levelString;
    FieldStruct threadName;
    FieldStruct refFlag;
    FieldStruct arg0;
    FieldStruct arg1;
    FieldStruct arg2;
    FieldStruct arg3;
    FieldStruct fileName;
    FieldStruct callerClass;
    FieldStruct method;
    FieldStruct callerLine;
    FieldStruct eventId;

    public ConfigurationStruct() {
        this.ts =           new FieldStruct("timestmp", 4, true);
        this.message =      new FieldStruct("message", 4, true);
        this.loggerName =   new FieldStruct("logger_name", 4, true);
        this.levelString =  new FieldStruct("level_string", 4, true);
        this.threadName =   new FieldStruct("thread_name", 4, true);
        this.refFlag =      new FieldStruct("ref_flag", 4, true);
        this.arg0 =         new FieldStruct("arg0", 4, true);
        this.arg1 =         new FieldStruct("arg1", 4, true);
        this.arg2 =         new FieldStruct("arg2", 4, true);
        this.arg3 =         new FieldStruct("arg3", 4, true);
        this.fileName =     new FieldStruct("filename", 4, true);
        this.callerClass =  new FieldStruct("filename", 4, true);
        this.method =       new FieldStruct("method", 4, true);
        this.callerLine =   new FieldStruct("caller_line", 4, true);
        this.eventId =      new FieldStruct("event_id", 4, true);
    }
}
