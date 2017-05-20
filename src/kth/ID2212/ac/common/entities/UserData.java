package kth.ID2212.ac.common.entities;

import java.io.Serializable;

/**
 * Details of current connected user.
 */
public class UserData implements Serializable {
    public String name;
    public long lastLogin;
    public boolean banned;
    public int warnings;

    public UserData(String name, long lastLogin, boolean banned, int warnings) {
        this.name = name;
        this.lastLogin = lastLogin;
        this.banned = banned;
        this.warnings = warnings;
    }

    public String getName() {
        return name;
    }

    public long getLastLogin() {
        return lastLogin;
    }

    public boolean getBanned() {
        return banned;
    }

    public int getWarnings() {
        return warnings;
    }
}
