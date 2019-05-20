package com.yudystriawan.complaintserver.controllers;

import com.yudystriawan.complaintserver.models.Instance;
import com.yudystriawan.complaintserver.repositories.InstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/instance")
public class InstanceController {

    @Autowired
    private InstanceRepository instanceRepository;

    @GetMapping("/all")
    public List<Instance> all(){
        return instanceRepository.findAll();
    }

}
