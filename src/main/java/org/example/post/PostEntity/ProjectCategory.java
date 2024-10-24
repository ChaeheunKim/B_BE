package org.example.post.PostEntity;

public enum ProjectCategory {
    Project("프로젝트"),
    Hackathon("해커톤");
    private final String description;

    ProjectCategory(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }




}
