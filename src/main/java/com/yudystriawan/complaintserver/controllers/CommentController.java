package com.yudystriawan.complaintserver.controllers;

import com.yudystriawan.complaintserver.models.Comment;
import com.yudystriawan.complaintserver.models.Complaint;
import com.yudystriawan.complaintserver.models.User;
import com.yudystriawan.complaintserver.models.request.CommentForm;
import com.yudystriawan.complaintserver.repositories.CommentRepository;
import com.yudystriawan.complaintserver.repositories.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @GetMapping("/all")
    public List<Comment> all(){
        return commentRepository.findAll();
    }

    //get comment by complaint id
    @GetMapping("/{complaintId}")
    public List<Comment> get(@PathVariable Integer complaintId){
        return commentRepository.findByComplaintId(complaintId);
    }

//    createcomment
    @PostMapping("/create/{complaintId}")
    public ResponseEntity<?> create(@RequestBody CommentForm form, @PathVariable Integer complaintId){
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Pengaduan tidak ditemukan"));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Comment comment = new Comment();
        comment.setBody(form.getBody());
        comment.setUser(user);
        comment.setComplaint(complaint);

        commentRepository.save(comment);

        return new ResponseEntity<>("Tindak lanjut berhasil dikirim", HttpStatus.OK);
    }


}
