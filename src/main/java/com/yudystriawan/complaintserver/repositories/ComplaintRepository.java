package com.yudystriawan.complaintserver.repositories;

import com.yudystriawan.complaintserver.models.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

    List<Complaint> findByInstanceNameAndNegative(String name, boolean negative);
    List<Complaint> findByInstanceName(String name);
    List<Complaint> findByNegative(boolean negative);
}
