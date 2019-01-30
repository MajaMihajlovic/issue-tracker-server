package com.issuetracker.issuetracker.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "project_has_user", schema = "issue_tracker_db", catalog = "")
@IdClass(ProjectHasUserPK.class)
public class ProjectHasUser {
    private int projectId;
    private int userId;

    @Id
    @Column(name = "project_id")
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Id
    @Column(name = "user_id")
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
        ProjectHasUser that = (ProjectHasUser) o;
        return projectId == that.projectId &&
                userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, userId);
    }
}
