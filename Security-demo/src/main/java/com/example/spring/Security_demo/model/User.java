package com.example.spring.Security_demo.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name="user")
public class User {

    @Id
    private int id;
    private String username;
    private String password;
}
