package com.issuetracker.issuetracker.repository;

import com.issuetracker.issuetracker.model.ProjectHasUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectHasUserRepository extends JpaRepository<ProjectHasUser, Integer> {

    List<ProjectHasUser>  findAllByProjectId(Integer id);

    ProjectHasUser findByProjectIdAndUserId(Integer id, Integer userId);
}
