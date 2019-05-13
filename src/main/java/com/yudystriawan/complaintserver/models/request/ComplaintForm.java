package com.yudystriawan.complaintserver.models.request;

import lombok.Data;

@Data
public class ComplaintForm {

    private String topic;
    private String body;
    private String category;

}
