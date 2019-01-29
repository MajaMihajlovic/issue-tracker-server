package com.issuetracker.issuetracker.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class Issue {
    private int id;
    private String title;
    private String description;
    private int stateId;
    private int priorityId;
    private Timestamp duedate;
    private Timestamp createdDate;
    private int typeId;
    private int reporterId;
    private int assigneeId;
    private int versionId;
    private int projectId;

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
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "state_id")
    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    @Basic
    @Column(name = "priority_id")
    public int getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(int priorityId) {
        this.priorityId = priorityId;
    }

    @Basic
    @Column(name = "duedate")
    public Timestamp getDuedate() {
        return duedate;
    }

    public void setDuedate(Timestamp duedate) {
        this.duedate = duedate;
    }

    @Basic
    @Column(name = "created_date")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "type_id")
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "reporter_id")
    public int getReporterId() {
        return reporterId;
    }

    public void setReporterId(int reporterId) {
        this.reporterId = reporterId;
    }

    @Basic
    @Column(name = "assignee_id")
    public int getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(int assigneeId) {
        this.assigneeId = assigneeId;
    }

    @Basic
    @Column(name = "version_id")
    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return id == issue.id &&
                stateId == issue.stateId &&
                priorityId == issue.priorityId &&
                typeId == issue.typeId &&
                reporterId == issue.reporterId &&
                assigneeId == issue.assigneeId &&
                versionId == issue.versionId &&
                Objects.equals(title, issue.title) &&
                Objects.equals(description, issue.description) &&
                Objects.equals(duedate, issue.duedate) &&
                Objects.equals(createdDate, issue.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, stateId, priorityId, duedate, createdDate, typeId, reporterId, assigneeId, versionId);
    }

    @Basic
    @Column(name = "project_id")
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
