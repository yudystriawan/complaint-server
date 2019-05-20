package com.yudystriawan.complaintserver.repositories;

import com.yudystriawan.complaintserver.models.Instance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstanceRepository extends JpaRepository<Instance, Integer> {

    Optional<Instance> findByName(String name);
}
