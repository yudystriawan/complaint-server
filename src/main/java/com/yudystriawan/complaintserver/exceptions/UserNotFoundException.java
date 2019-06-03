package com.yudystriawan.complaintserver.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super(username+" not found");
    }
}
