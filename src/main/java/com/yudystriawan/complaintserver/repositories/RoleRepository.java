package com.yudystriawan.complaintserver.repositories;

import com.yudystriawan.complaintserver.models.Role;
import com.yudystriawan.complaintserver.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(RoleName name);

}
