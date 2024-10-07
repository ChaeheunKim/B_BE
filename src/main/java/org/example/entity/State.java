package org.example.entity;

public enum State {


        accept("승인"),
        waiting("대기"),
        refuse("부회장");
        private final String description;

        State (String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }


    }


