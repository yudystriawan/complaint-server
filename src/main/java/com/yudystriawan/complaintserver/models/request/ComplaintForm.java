package com.yudystriawan.complaintserver.models.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ComplaintForm {

    @NotBlank
    private String topic;

    @NotBlank
    private String body;

    @NotBlank
    private String category;

}
