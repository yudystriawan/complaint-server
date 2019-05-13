package com.yudystriawan.complaintserver.repositories;

import com.yudystriawan.complaintserver.models.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
}
