package org.example.domain.user.UserEntity;

public enum State {

    approved("승인"),
    pending("대기"),
    rejected("거절");
        private final String description;

        State (String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }


    }


