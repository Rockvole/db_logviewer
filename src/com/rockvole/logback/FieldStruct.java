package com.rockvole.logback;

public class FieldStruct {
    String name;
    int minWidth;
    boolean show;
    public FieldStruct(String name, int minWidth, boolean show) {
        this.name = name;
        this.minWidth = minWidth;
        this.show = show;
    }
}
