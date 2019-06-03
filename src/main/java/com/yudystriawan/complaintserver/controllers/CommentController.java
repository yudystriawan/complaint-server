package com.yudystriawan.complaintserver.controllers;

import com.yudystriawan.complaintserver.models.Comment;
import com.yudystriawan.complaintserver.models.Complaint;
import com.yudystriawan.complaintserver.models.User;
import com.yudystriawan.complaintserver.repositories.CommentRepository;
import com.yudystriawan.complaintserver.repositories.ComplaintRepository;
import com.yudystriawan.complaintserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Comment> all() {
        return commentRepository.findAll();
    }

    //get comment by complaint id
    @GetMapping("/{complaintId}")
    public List<Comment> get(@PathVariable Integer complaintId) {
        return commentRepository.findByComplaintId(complaintId);
    }

    //    createcomment
    @PostMapping("/{complaintId}")
    @PreAuthorize("hasRole('ADMIN_INST')")
    public ResponseEntity<?> create(@RequestBody Comment comment, @PathVariable Integer complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Pengaduan tidak ditemukan"));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Comment newComment = new Comment();
        newComment.setBody(comment.getBody());
        newComment.setUser(user);
        newComment.setComplaint(complaint);

        commentRepository.save(newComment);

        return new ResponseEntity<>("Tindak lanjut berhasil dikirim", HttpStatus.OK);
    }


}
