package com.rockvole.logback.data;

import com.rockvole.logback.data.FieldStruct;

public class ConfigurationStruct {
    public FieldStruct ts;
    public FieldStruct message;
    public FieldStruct loggerName;
    public FieldStruct levelString;
    public FieldStruct threadName;
    public FieldStruct refFlag;
    public FieldStruct arg0;
    public FieldStruct arg1;
    public FieldStruct arg2;
    public FieldStruct arg3;
    public FieldStruct fileName;
    public FieldStruct callerClass;
    public FieldStruct method;
    public FieldStruct callerLine;
    public FieldStruct eventId;

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