package com.rockvole.logback.data;

public class FieldStruct {
    public String name;
    public boolean show;
    public int minWidth;

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
