package org.example.user.UserEntity;


public enum Role {
         //권한이 세 개인가요..?

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
