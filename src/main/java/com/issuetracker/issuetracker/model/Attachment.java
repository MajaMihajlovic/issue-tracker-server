package com.issuetracker.issuetracker.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Attachment {
    private int id;
    private byte[] file;
    private int issueId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "file")
    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Basic
    @Column(name = "issue_id")
    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachment that = (Attachment) o;
        return id == that.id &&
                issueId == that.issueId &&
                Arrays.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, issueId);
        result = 31 * result + Arrays.hashCode(file);
        return result;
    }
}
