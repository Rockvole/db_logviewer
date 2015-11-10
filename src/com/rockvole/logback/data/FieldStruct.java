package com.rockvole.logback.data;

public class FieldStruct {
    public String name;
    public boolean show;
    public int minWidth;
    public String like;

    public FieldStruct(String name, boolean show, int minWidth, String like) {
        this.name = name;
        this.show = show;
        this.minWidth = minWidth;
        this.like = like;
    }

    @Override
    public String toString() {
        return "FieldStruct{" +
                "name='" + name + '\'' +
                ", show=" + show +
                ", minWidth=" + minWidth +
                ", like=" + like +
                '}';
    }
}
