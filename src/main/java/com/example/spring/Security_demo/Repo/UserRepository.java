package com.example.spring.Security_demo.Repo;
import com.example.spring.Security_demo.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository

public interface UserRepository extends JpaRepository<User, Integer> {
    //@Query(value = "Select * from user where username=?1", nativeQuery = true)

    User findByUsername(String username);
}
