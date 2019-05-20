package com.yudystriawan.complaintserver.models.request;

import com.yudystriawan.complaintserver.models.Instance;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.graalvm.compiler.phases.schedule.SchedulePhase;

@Setter
@Getter
@ToString
public class EditComplaintForm {

    private String instance;
    private boolean negative;


}
