package com.yudystriawan.complaintserver.controllers;

import com.yudystriawan.complaintserver.exceptions.ComplaintNotFoundException;
import com.yudystriawan.complaintserver.models.Complaint;
import com.yudystriawan.complaintserver.models.Instance;
import com.yudystriawan.complaintserver.models.User;
import com.yudystriawan.complaintserver.models.request.ComplaintForm;
import com.yudystriawan.complaintserver.models.request.EditComplaintForm;
import com.yudystriawan.complaintserver.prediction.Classification;
import com.yudystriawan.complaintserver.repositories.ComplaintRepository;
import com.yudystriawan.complaintserver.repositories.InstanceRepository;
import com.yudystriawan.complaintserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InstanceRepository instanceRepository;

    @GetMapping
    public List<Complaint> all() {
        return complaintRepository.findAll();
    }

    @GetMapping(params = "instance")
    public List<Complaint> byInstance(@RequestParam("instance") String instanceName) {
        return complaintRepository.findByInstanceName(instanceName);
    }

    @GetMapping(params = "negative")
    public List<Complaint> byNegative(@RequestParam("negative") boolean negative) {
        return complaintRepository.findByNegative(negative);
    }

    @GetMapping(params = {"instance", "negative"})
    public List<Complaint> byInstanceAndNegative(@RequestParam("instance") String instanceName, @RequestParam("negative") boolean negative) {
        return complaintRepository.findByInstanceNameAndNegative(instanceName, negative);
    }

    @GetMapping("/{id}")
    public Complaint one(@PathVariable Integer id) {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new ComplaintNotFoundException(id));
    }


    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public @ResponseBody
    String newComplaint(@RequestBody ComplaintForm form) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userRepository.findByUsername("user")
//                .orElseThrow(() -> new UsernameNotFoundException("Account not found"));

        Complaint complaint = new Complaint(form);

        //prediction
        String body = form.getBody();
        Classification classification = new Classification(body);
        String pred_instance = classification.getInstance();

        Instance instance = instanceRepository.findByName(pred_instance)
                .orElseThrow(() -> new RuntimeException("Nama Instansi tidak ditemukan"));

        complaint.setInstance(instance);
        complaint.setPercent(classification.getPercent());
        complaint.setUser(user);
        complaintRepository.save(complaint);

        return "Pengaduan terkirim";

    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN_PRO')")
    public ResponseEntity<?> edit(@RequestBody EditComplaintForm form, @PathVariable Integer id) {

        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pengaduan tidak ditemukan"));

        Instance instance = instanceRepository.findByName(form.getInstance())
                .orElseThrow(() -> new RuntimeException("Nama Instansi tidak ditemukan"));

        complaint.setInstance(instance);
        complaint.setNegative(form.isNegative());

        complaintRepository.save(complaint);

        return new ResponseEntity<>("Pengaduan berhasil diubah", HttpStatus.OK);

    }

}
