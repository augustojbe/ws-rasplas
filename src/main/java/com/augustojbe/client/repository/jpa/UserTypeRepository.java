package com.augustojbe.client.repository.jpa;

import com.augustojbe.client.model.jpa.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    Optional<UserType> findByName(String name);
}
