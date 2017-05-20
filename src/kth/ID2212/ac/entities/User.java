package kth.ID2212.ac.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kth.ID2212.ac.common.entities.UserData;

import javax.persistence.*;
import java.time.Instant;

/**
 * User entity which used to persist information about user.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = User.FIND_ALL, query = "SELECT u FROM User u ORDER BY u.name DESC"),
        @NamedQuery(name = User.FIND_BY_LOGIN_PASSWORD, query = "SELECT u FROM User u WHERE u.name = :login AND u.password = :password"),
        @NamedQuery(name = User.FIND_ADMIN_BY_LOGIN_PASSWORD, query = "SELECT u FROM User u WHERE u.name = :login AND u.password = :password AND u.role = '" + User.ROLE_ADMIN + "'"),
        @NamedQuery(name = User.FIND_BY_LOGIN, query = "SELECT u FROM User u WHERE u.name = :login"),
        @NamedQuery(name = User.FIND_BY_ID, query = "SELECT u FROM User u WHERE u.id = :id"),
        @NamedQuery(name = User.COUNT_ALL, query = "SELECT COUNT(u) FROM User u")
})
@Table(name = "users", schema = "anticheat")
public class User {
    public static final String FIND_ALL = "User.findAll";
    public static final String COUNT_ALL = "User.countAll";
    public static final String FIND_BY_ID = "User.findById";
    public static final String FIND_BY_LOGIN_PASSWORD = "User.findByLoginAndPassword";
    public static final String FIND_ADMIN_BY_LOGIN_PASSWORD = "User.findAdminByLoginAndPassword";
    public static final String FIND_BY_LOGIN = "User.findByLogin";
    public static final String ROLE_USER = "user";
    public static final String ROLE_ADMIN = "admin";

    public static final int MAX_WARNINGS = 5;

    private int id;
    private String name;
    private String password;
    private String role = ROLE_USER;
    private long lastLogin;
    private long oldLastLogin;
    private boolean banned = false;
    private boolean online = false;
    private int warnings = 0;

    public User() {
    }

    @PrePersist
    public void initNewUser() {
        lastLogin = Instant.now().getEpochSecond();
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "password")
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Basic
    @Column(name = "last_login")
    public long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void afterLogin() {
        oldLastLogin = lastLogin;
        online = true;
        setLastLogin(Instant.now().getEpochSecond());
    }

    @Basic
    @Column(name = "banned")
    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    @Basic
    @Column(name = "online")
    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Basic
    @Column(name = "warnings")
    public int getWarnings() {
        return warnings;
    }

    public void setWarnings(int warnings) {
        this.warnings = warnings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (lastLogin != user.lastLogin) return false;
        if (banned != user.banned) return false;
        if (online != user.online) return false;
        if (warnings != user.warnings) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    public UserData extractData() {
        return new UserData(name, oldLastLogin, banned, warnings);
    }
}
