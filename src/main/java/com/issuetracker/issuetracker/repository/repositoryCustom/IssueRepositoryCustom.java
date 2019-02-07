package com.issuetracker.issuetracker.repository.repositoryCustom;

import com.issuetracker.issuetracker.model.Issue;
import com.issuetracker.issuetracker.model.modelCustom.IssueCustom;
import com.issuetracker.issuetracker.model.modelCustom.IssueType;

import java.util.List;

public interface IssueRepositoryCustom {

    List<IssueCustom> getAllIssues();
    IssueCustom getById(Integer id);
    List<IssueType> countIssueType(Integer projectId);
    List<IssueType> countIssuePriority(Integer projectId);
    List<IssueType> countIssueState(Integer projectId);
}
