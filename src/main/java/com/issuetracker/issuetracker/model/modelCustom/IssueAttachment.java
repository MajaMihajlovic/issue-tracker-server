package com.issuetracker.issuetracker.model.modelCustom;

import com.issuetracker.issuetracker.model.Attachment;
import com.issuetracker.issuetracker.model.Issue;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

public class IssueAttachment {

    private Issue issue;
    private List<Attachment> list;
    private IssueCustom issueCustom;

    public IssueCustom getIssueCustom() {
        return issueCustom;
    }

    public void setIssueCustom(IssueCustom issueCustom) {
        this.issueCustom = issueCustom;
    }

    public IssueAttachment(Issue issue, List<Attachment> list) {
        this.issue = issue;
        this.list = list;
    }

    public IssueAttachment() {
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public List<Attachment> getList() {
        return list;
    }

    public void setList(List<Attachment> list) {
        this.list = list;
    }
}
