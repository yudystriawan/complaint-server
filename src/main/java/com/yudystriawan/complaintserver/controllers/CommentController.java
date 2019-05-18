package com.yudystriawan.complaintserver.controllers;

import com.yudystriawan.complaintserver.models.Comment;
import com.yudystriawan.complaintserver.repositories.CommentRepository;
import com.yudystriawan.complaintserver.repositories.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    //createcomment
//    @PostMapping("/create/{complaintId}")
//    public  Comment new(@PathVariable Integer complaintId, Comp)


}
