package com.issuetracker.issuetracker.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class ProjectHasUserPK implements Serializable {
    private int projectId;
    private int userId;

    @Column(name = "project_id")
    @Id
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Column(name = "user_id")
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectHasUserPK that = (ProjectHasUserPK) o;
        return projectId == that.projectId &&
                userId == that.userId;
    }

    @Override
    public String toString() {
        return "ProjectHasUserPK{" +
                "projectId=" + projectId +
                ", userId=" + userId +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, userId);
    }
}
