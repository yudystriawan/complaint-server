package com.yudystriawan.complaintserver.controllers;

import com.yudystriawan.complaintserver.exceptions.ComplaintNotFoundException;
import com.yudystriawan.complaintserver.models.Complaint;
import com.yudystriawan.complaintserver.models.User;
import com.yudystriawan.complaintserver.models.request.ComplaintForm;
import com.yudystriawan.complaintserver.prediction.Classification;
import com.yudystriawan.complaintserver.repositories.ComplaintRepository;
import com.yudystriawan.complaintserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public List<Complaint> all(){
        return complaintRepository.findAll();
    }

    @GetMapping("/{id}")
    public Complaint one(@PathVariable Integer id){
        return complaintRepository.findById(id)
                .orElseThrow(() -> new ComplaintNotFoundException(id));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public Complaint newComplaint(@RequestBody ComplaintForm form){
//
//        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        User user = userRepository.findByUsername(customUserDetails.getUsername())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Complaint complaint = new Complaint(form);

        //prediction
        String body = form.getBody();
        Classification classification = new Classification(body);
        complaint.setInstance(classification.getInstance());
        complaint.setPercent(classification.getPercent());
//        complaint.setUser(user);

        return complaintRepository.save(complaint);

    }

}
