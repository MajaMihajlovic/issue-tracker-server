package com.issuetracker.issuetracker.model.modelCustom;

import com.issuetracker.issuetracker.model.User;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;
import java.io.Serializable;
import java.util.Date;

@SqlResultSetMapping(
        name = "UserCustom",
        classes = @ConstructorResult(
                targetClass = UserCustom.class,
                columns = {
                        @ColumnResult(name="id"),
                        @ColumnResult(name="email"),
                        @ColumnResult(name="full_name")
                        }
        )
)
@MappedSuperclass
public class UserCustom implements Serializable {

    private Integer id;
    private String email;
    private String fullName;

    public UserCustom(Integer id, String email, String fullName) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
    }

    public UserCustom() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
