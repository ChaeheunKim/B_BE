package org.example.entity;

public enum Part {
    PD("디자인"),

    PM("기획"),
    BE("백엔드"),
    FE("프론트엔드");
    private final String description;

    Part (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
