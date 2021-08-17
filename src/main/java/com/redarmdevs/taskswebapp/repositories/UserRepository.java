package com.redarmdevs.taskswebapp.repositories;

import com.redarmdevs.taskswebapp.models.Task;
import com.redarmdevs.taskswebapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}