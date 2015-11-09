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
        this.ts =           new FieldStruct("timestmp", true, 4);
        this.message =      new FieldStruct("message", true, 4);
        this.loggerName =   new FieldStruct("logger_name", true, 4);
        this.levelString =  new FieldStruct("level_string", true, 4);
        this.threadName =   new FieldStruct("thread_name", true, 4);
        this.refFlag =      new FieldStruct("ref_flag", true, 4);
        this.arg0 =         new FieldStruct("arg0", true, 4);
        this.arg1 =         new FieldStruct("arg1", true, 4);
        this.arg2 =         new FieldStruct("arg2", true, 4);
        this.arg3 =         new FieldStruct("arg3", true, 4);
        this.fileName =     new FieldStruct("filename", true, 4);
        this.callerClass =  new FieldStruct("caller_class", true, 4);
        this.method =       new FieldStruct("method", true, 4);
        this.callerLine =   new FieldStruct("caller_line", true, 4);
        this.eventId =      new FieldStruct("event_id", true, 4);
    }
}
