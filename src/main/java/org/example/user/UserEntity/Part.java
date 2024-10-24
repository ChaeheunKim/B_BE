package org.example.user.UserEntity;

public enum Part {

    All("전체"),

    PM("PM"),
    FE("FE"),
    BE("BE"),

    PD("PD");
    private final String description;

    Part (String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

}

