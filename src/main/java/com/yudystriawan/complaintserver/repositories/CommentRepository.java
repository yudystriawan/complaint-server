package com.yudystriawan.complaintserver.repositories;

import com.yudystriawan.complaintserver.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByComplaintId(Integer id);
}
