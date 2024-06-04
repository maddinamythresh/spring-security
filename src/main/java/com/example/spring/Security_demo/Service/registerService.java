package com.example.spring.Security_demo.Service;

import com.example.spring.Security_demo.Repo.UserRepository;
import com.example.spring.Security_demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class registerService {

    @Autowired
    private UserRepository repo;

    public User registeservice(User user){
        repo.save(user);
        return user;
    }

}
