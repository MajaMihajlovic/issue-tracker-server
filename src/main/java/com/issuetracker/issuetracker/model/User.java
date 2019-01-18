package com.issuetracker.issuetracker.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
@SqlResultSetMapping(
        name = "UserMapping",
        classes = @ConstructorResult(
                targetClass = User.class,
                columns = {
                        @ColumnResult(name="id"),
                        @ColumnResult(name="email"),
                        @ColumnResult(name="username"),
                        @ColumnResult(name="password"),
                        @ColumnResult(name="full_name"),
                        @ColumnResult(name="photo"),
                        @ColumnResult(name="active"),
                        @ColumnResult(name="token"),
                        @ColumnResult(name="token_time", type = Date.class),
                }
        )
)
@Entity
public class User {
    private int id;
    private String username;
    private byte[] password;
    private String email;
    private String fullName;
    private Byte active;
    private Byte deleted;
    private byte[] photo;
    private String token;
    private Time tokenTime;
    private String isAdmin;

    public User() {
    }

    public User(int id, String username, byte[] password, String email, String fullName, Byte active, Byte deleted, byte[] photo, String token, Time tokenTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.active = active;
        this.deleted = deleted;
        this.photo = photo;
        this.token = token;
        this.tokenTime = tokenTime;
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
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(username, user.username) &&
                Arrays.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(fullName, user.fullName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, username, email, fullName);
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }

    @Basic
    @Column(name = "active")
    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }

    @Basic
    @Column(name = "deleted")
    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "photo")
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "token_time")
    public Time getTokenTime() {
        return tokenTime;
    }

    public void setTokenTime(Time tokenTime) {
        this.tokenTime = tokenTime;
    }

    @Basic
    @Column(name = "is_admin")
    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }
}
