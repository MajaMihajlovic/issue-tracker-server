package com.issuetracker.issuetracker.model;

import javax.persistence.*;
import java.util.Objects;
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class Priority {
    private int id;
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Priority priority = (Priority) o;
        return id == priority.id &&
                Objects.equals(name, priority.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
