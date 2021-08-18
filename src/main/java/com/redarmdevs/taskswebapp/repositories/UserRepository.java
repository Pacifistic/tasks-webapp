package com.redarmdevs.taskswebapp.repositories;

import com.redarmdevs.taskswebapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}