package com.rockvole.logback;

public class FieldStruct {
    String name;
    boolean show;
    int minWidth;
    public FieldStruct(String name, boolean show, int minWidth) {
        this.name = name;
        this.show = show;
        this.minWidth = minWidth;
    }

    @Override
    public String toString() {
        return "FieldStruct{" +
                "name='" + name + '\'' +
                ", show=" + show +
                ", minWidth=" + minWidth +
                '}';
    }
}
