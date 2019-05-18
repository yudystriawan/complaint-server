package com.yudystriawan.complaintserver.repositories;

import com.yudystriawan.complaintserver.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByComplaintId(Integer id);
}
