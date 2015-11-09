package com.rockvole.logback.tools;

import com.rockvole.logback.data.ConfigurationStruct;
import com.rockvole.logback.data.FieldStruct;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class PropertyFileTools {
    public static ConfigurationStruct fetchProperties(String filename) {
        if(filename==null) filename="../../../resources/logback.properties";
        else filename+="../../../";
        Configuration configuration = null;
        try {
            configuration = new PropertiesConfiguration(filename);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        ConfigurationStruct struct = new ConfigurationStruct();
        struct.ts = getFieldStructFromConfiguration(struct.ts, configuration);
        struct.message = getFieldStructFromConfiguration(struct.message, configuration);
        struct.loggerName = getFieldStructFromConfiguration(struct.loggerName, configuration);
        struct.levelString = getFieldStructFromConfiguration(struct.levelString, configuration);
        struct.threadName = getFieldStructFromConfiguration(struct.threadName, configuration);
        struct.refFlag = getFieldStructFromConfiguration(struct.refFlag, configuration);
        struct.arg0 = getFieldStructFromConfiguration(struct.arg0, configuration);
        struct.arg1 = getFieldStructFromConfiguration(struct.arg1, configuration);
        struct.arg2 = getFieldStructFromConfiguration(struct.arg2, configuration);
        struct.arg3 = getFieldStructFromConfiguration(struct.arg3, configuration);
        struct.fileName = getFieldStructFromConfiguration(struct.fileName, configuration);
        struct.callerClass = getFieldStructFromConfiguration(struct.callerClass, configuration);
        struct.method = getFieldStructFromConfiguration(struct.method, configuration);
        struct.callerLine = getFieldStructFromConfiguration(struct.callerLine, configuration);
        struct.eventId = getFieldStructFromConfiguration(struct.eventId, configuration);
        return struct;
    }

    private static FieldStruct getFieldStructFromConfiguration(FieldStruct struct, Configuration configuration) {
        struct.show = true;
        if(configuration.containsKey(struct.name+".show")) struct.show=configuration.getBoolean(struct.name+".show");

        struct.minWidth=0;
        if(struct.show) {
            if (configuration.containsKey(struct.name + ".minwidth"))
                struct.minWidth = configuration.getInt(struct.name + ".minwidth") + DisplayTable.SPC_WIDTH;
        }
        return struct;
    }

}
