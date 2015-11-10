package com.rockvole.logback.data;

public class FieldStruct {
    public String name;
    public boolean show;
    public int minWidth;
    public String like;
    public String notLike;

    public FieldStruct(String name, boolean show, int minWidth, String like, String notLike) {
        this.name = name;
        this.show = show;
        this.minWidth = minWidth;
        this.like = like;
        this.notLike = notLike;
    }

    @Override
    public String toString() {
        return "FieldStruct{" +
                "name='" + name + '\'' +
                ", show=" + show +
                ", minWidth=" + minWidth +
                ", like=" + like +
                ", notLike=" + notLike +
                '}';
    }
}
