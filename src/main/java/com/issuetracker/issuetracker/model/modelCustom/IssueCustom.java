package com.issuetracker.issuetracker.model.modelCustom;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
@SuppressWarnings("WeakerAccess")
@SqlResultSetMapping(
        name = "IssueMapping",
        classes = @ConstructorResult(
                targetClass = IssueCustom.class,
                columns = {
                        @ColumnResult(name="id"),
                        @ColumnResult(name="title"),
                        @ColumnResult(name="description"),
                        @ColumnResult(name="state"),
                        @ColumnResult(name="priority"),
                        @ColumnResult(name="duedate", type=Date.class),
                        @ColumnResult(name="created_date", type=Date.class),
                        @ColumnResult(name="type"),
                        @ColumnResult(name="full_name"),
                        @ColumnResult(name="assignee_full_name"),
                        @ColumnResult(name="assignee_id"),
                        @ColumnResult(name="version"),
                        @ColumnResult(name="project_name"),
                        @ColumnResult(name="project_id")
                }
        )
)

@MappedSuperclass
public class IssueCustom implements Serializable {
    @Override
    public String toString() {
        return "IssueCustom{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                ", priority='" + priority + '\'' +
                ", duedate=" + duedate +
                ", createdDate=" + createdDate +
                ", type='" + type + '\'' +
                ", reporterFullName='" + reporterFullName + '\'' +
                ", assigneeFullName='" + assigneeFullName + '\'' +
                ", assigneId=" + assigneId +
                ", version='" + version + '\'' +
                ", projectName='" + projectName + '\'' +
                '}';
    }

    private int id;
    private String title;
    private String description;
    private String state;
    private String priority;
    private Timestamp duedate;
    private Timestamp createdDate;
    private String type;
    private String reporterFullName;
    private String assigneeFullName;
    private int assigneId;
    private String version;
    private String projectName;
    private int projectId;
    public IssueCustom() {
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public IssueCustom(int id, String title, String description, String state, String priority, Date duedate,
                       Date createdDate, String type, String reporterFullName, String assigneeFullName, int assigneId, String version, String projectName, Integer projectId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = state;
        this.priority = priority;
        this.duedate = new Timestamp(duedate.getTime());
        this.createdDate = new Timestamp(createdDate.getTime());
        this.type = type;
        this.reporterFullName = reporterFullName;
        this.assigneeFullName = assigneeFullName;
        this.assigneId = assigneId;
        this.version = version;
        this.projectName = projectName;
        this.projectId=projectId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Timestamp getDuedate() {
        return duedate;
    }

    public void setDuedate(Timestamp duedate) {
        this.duedate = duedate;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReporterFullName() {
        return reporterFullName;
    }

    public void setReporterFullName(String reporterFullName) {
        this.reporterFullName = reporterFullName;
    }

    public String getAssigneeFullName() {
        return assigneeFullName;
    }

    public void setAssigneeFullName(String assigneeFullName) {
        this.assigneeFullName = assigneeFullName;
    }

    public int getAssigneId() {
        return assigneId;
    }

    public void setAssigneId(int assigneId) {
        this.assigneId = assigneId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
