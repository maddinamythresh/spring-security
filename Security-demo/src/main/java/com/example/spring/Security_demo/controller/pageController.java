package com.example.spring.Security_demo.controller;

import com.example.spring.Security_demo.Service.registerService;
import com.example.spring.Security_demo.model.Student;
import com.example.spring.Security_demo.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class pageController {


    BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    @Autowired
    private registerService service;


    @GetMapping("/token")
    public CsrfToken getToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }

    List<Student> students=new ArrayList<>(List.of
            (new Student(20,"Harsh","Python"),new Student(10,"Mythresh","java"))
    );
    @PostMapping("/students")
    public void addStudent(@RequestBody Student student){
        students.add(student);
    }

    @GetMapping("/students")
    public List<Student> getStudents(){
        return students;
    }
    @GetMapping("/")
    public String sayHello(HttpServletRequest request){
        return  "Hello"+request.getSession().getId();
    }

    @PostMapping("requests")
    public User register(@RequestBody User user){
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        return  service.registeservice(user);
    }
}
