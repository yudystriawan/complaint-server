package com.yudystriawan.complaintserver.advices;

import com.yudystriawan.complaintserver.exceptions.ComplaintNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ComplaintNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ComplaintNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String complaintNotFoundHandler(ComplaintNotFoundException e) {
        return e.getMessage();
    }
}
