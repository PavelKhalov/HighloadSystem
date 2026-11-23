package ru.khalov.authservice.entity;

import jakarta.persistence.*;
import ru.khalov.authservice.util.Role;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long publicId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public User(){}

    public User(String email, String username, String password, Role role){
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UUID getId() {return id;}
    public Long getPublicId() {return publicId;}
    public String getEmail() {return email;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public Role getRole() {return role;}
    public Instant getCreatedAt() {return createdAt;}

    @PrePersist
    public void prePersist(){
        this.createdAt = Instant.now();
    }
}
