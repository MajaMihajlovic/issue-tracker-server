package com.issuetracker.issuetracker.model.modelCustom;

import com.issuetracker.issuetracker.model.ParticipantInfo;
import com.issuetracker.issuetracker.model.Project;

import java.util.List;

public class ProjectCustom {

    private Project project;
    private List<ParticipantInfo> list;

    public ProjectCustom() {
    }

    public List<ParticipantInfo> getList() {
        return list;
    }

    public void setList(List<ParticipantInfo> list) {
        this.list = list;
    }

    public ProjectCustom(Project project, List<ParticipantInfo> list) {
        this.project = project;
        this.list = list;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
