package com.issuetracker.issuetracker.repository;

import com.issuetracker.issuetracker.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository  extends JpaRepository<Project, Integer> {

    List<Project> getAllByFinnishedEquals(byte value);
}
