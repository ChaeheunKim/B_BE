package org.example.entity;

import jakarta.persistence.Entity;


public enum Role {

        USER("동아리멤버"),

        ADMIN("부회장");
        private final String description;

        Role (String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }


}
