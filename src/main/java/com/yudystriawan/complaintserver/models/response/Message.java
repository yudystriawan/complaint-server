package com.yudystriawan.complaintserver.models.response;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
public class Message {

    private int id;
    private String subject;
    private String text;

    public Message(int id, String subject, String text) {
        this.id = id;
        this.subject = subject;
        this.text = text;
    }
}
