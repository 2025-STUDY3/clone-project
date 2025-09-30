package com.ll.clone_coding.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(length = 50, nullable = true)
    private String nickname;

    @Column(length = 50, nullable = true, unique = true)
    private String email;

    @Column(length = 255, nullable = true)
    private String school;

    @Column(nullable = false)
    private String password;

    @CreatedDate
    @Column(updatable = false, nullable = true)
    private LocalDateTime createdAt;
}