package com.yudystriawan.complaintserver.models.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class EditComplaintForm {

    private String instance;
    private boolean negative;


}
