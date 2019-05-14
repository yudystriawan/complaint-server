package com.yudystriawan.complaintserver.models.response;

import lombok.Data;

@Data
public class JwtTokenResponse {

    private String token;
    private String type = "Bearer ";

    public JwtTokenResponse(String token) {
        this.token = token;
    }
}
