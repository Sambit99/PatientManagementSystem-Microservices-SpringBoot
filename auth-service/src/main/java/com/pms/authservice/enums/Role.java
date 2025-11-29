package com.pms.authservice.enums;

public enum Role {
    ADMIN(3),
    MODERATOR(2),
    USER(1);

    private final int level;

    Role(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public boolean hasAccess(Role requiredRole) {
        return this.level >= requiredRole.getLevel();
    }
}
