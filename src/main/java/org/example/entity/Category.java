package org.example.entity;

public enum Category {

    Project("프로젝트"),
    Seminar("세미나"),
    Study("스터디"),
    Networking("네트워킹");
    private final String description;

    Category (String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

}
