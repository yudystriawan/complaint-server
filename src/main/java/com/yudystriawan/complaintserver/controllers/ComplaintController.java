package com.yudystriawan.complaintserver.controllers;

import com.yudystriawan.complaintserver.exceptions.ComplaintNotFoundException;
import com.yudystriawan.complaintserver.models.Complaint;
import com.yudystriawan.complaintserver.models.request.ComplaintForm;
import com.yudystriawan.complaintserver.prediction.Classification;
import com.yudystriawan.complaintserver.repositories.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ComplaintController {

    @Autowired
    private ComplaintRepository complaintRepository;

    @GetMapping("/complaints")
    public List<Complaint> all(){
        return complaintRepository.findAll();
    }

    @GetMapping("complaint/{id)")
    public Complaint one(@PathVariable Integer id){
        return complaintRepository.findById(id)
                .orElseThrow(() -> new ComplaintNotFoundException(id));
    }

    @PostMapping("/complaint")
    public Complaint newComplaint(@RequestBody ComplaintForm form){
        Complaint complaint = new Complaint(form);

        //prediction
        String body = form.getBody();
        Classification classification = new Classification(body);

        complaint.setInstance(classification.getInstance());
        complaint.setPercent(classification.getPercent());

        return complaintRepository.save(complaint);

    }

}
