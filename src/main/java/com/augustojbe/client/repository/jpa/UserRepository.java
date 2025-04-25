package com.augustojbe.client.repository.jpa;

import com.augustojbe.client.model.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
