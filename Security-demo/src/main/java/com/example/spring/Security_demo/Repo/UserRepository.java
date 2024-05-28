package com.example.spring.Security_demo.Repo;
import com.example.spring.Security_demo.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByusername(String username);
}
