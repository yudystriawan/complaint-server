package com.yudystriawan.complaintserver.models;

import com.yudystriawan.complaintserver.models.request.ComplaintForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String topic;
    private String body;
    private String category;

    @ManyToOne
    private Instance instance;

    private boolean negative;
    private double percent;

    @ManyToOne
    private User user;

    @OneToMany
    private List<Comment> comments;

    @CreationTimestamp
    private Timestamp created_at;

    @UpdateTimestamp
    private Timestamp updated_at;

    public Complaint(ComplaintForm form) {
        this.topic = form.getTopic();
        this.body = form.getBody();
        this.category = form.getCategory();
    }
}
