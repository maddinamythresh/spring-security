package com.example.spring.Security_demo.Service;

import com.example.spring.Security_demo.Repo.UserRepository;
import com.example.spring.Security_demo.model.User;
import com.example.spring.Security_demo.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private  UserRepository repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("In user details Service"+username+"in");
        User user=repo.findByUsername(username);

        System.out.println(user);
        if(user==null) {
            System.out.println("User404");
            throw new UsernameNotFoundException("User404");
        }
        return new UserPrincipal(user);
    }
}
