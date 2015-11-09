package com.rockvole.logback.data;

public class FieldStruct {
    public String name;
    public boolean show;
    public int minWidth;
    public String filter;

    public FieldStruct(String name, boolean show, int minWidth, String filter) {
        this.name = name;
        this.show = show;
        this.minWidth = minWidth;
        this.filter = filter;
    }

    @Override
    public String toString() {
        return "FieldStruct{" +
                "name='" + name + '\'' +
                ", show=" + show +
                ", minWidth=" + minWidth +
                ", filter=" + filter +
                '}';
    }
}
