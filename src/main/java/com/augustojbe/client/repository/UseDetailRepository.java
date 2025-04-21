package com.augustojbe.client.repository;

import com.augustojbe.client.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UseDetailRepository extends JpaRepository<UserCredential, Long> {

    Optional<UserCredential> findByUsername(String username);
}
