package com.issuetracker.issuetracker.repository;

import com.issuetracker.issuetracker.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {

  List<Attachment> getByIssueId(Integer id);
  
}
