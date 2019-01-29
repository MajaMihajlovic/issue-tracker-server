package com.issuetracker.issuetracker.model;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
@SqlResultSetMapping(
        name = "UserMapping",
        classes = @ConstructorResult(
                targetClass = User.class,
                columns = {
                        @ColumnResult(name="id"),

                        @ColumnResult(name="username"),
                        @ColumnResult(name="password"),
                        @ColumnResult(name="email"),
                        @ColumnResult(name="full_name"),

                        @ColumnResult(name="active",type = Byte.class),
                        @ColumnResult(name="deleted",type = Byte.class),
                        @ColumnResult(name="photo"),
                        @ColumnResult(name="token"),
                        @ColumnResult(name="token_time",type = Date.class),
                        @ColumnResult(name="is_admin",type = Byte.class)
                }
        )
)
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private Byte active;
    private Byte deleted;
    private byte[] photo;
    private String token;
    private Timestamp tokenTime;
    private Byte isAdmin;
    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", active=" + active +
                ", deleted=" + deleted +
                ", photo=" + Arrays.toString(photo) +
                ", token='" + token + '\'' +
                ", tokenTime=" + tokenTime +
                ", isAdmin='" + isAdmin + '\'' +
                '}';
    }

    public User(int id, String username, String password, String email, String fullName, Byte active, Byte deleted,byte[] photo, String token, Date token_time,Byte isAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.active = active;
        this.deleted = deleted;
        this.photo = photo;
        this.token = token;
        this.isAdmin=isAdmin;
        setTokenTime(token_time==null ? null:new Timestamp(token_time.getTime()));
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(fullName, user.fullName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, username, password, email, fullName, active, deleted, token, tokenTime, isAdmin);
        result = 31 * result + Arrays.hashCode(photo);
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
    public Timestamp getTokenTime() {
        return tokenTime;
    }

    public void setTokenTime(Timestamp tokenTime) {
        this.tokenTime = tokenTime;
    }
    @Basic
    @Column(name = "is_admin")
    public Byte getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Byte isAdmin) {
        this.isAdmin = isAdmin;
    }
}
