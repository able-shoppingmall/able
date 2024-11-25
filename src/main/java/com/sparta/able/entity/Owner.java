package com.sparta.able.entity;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "owners")
@NoArgsConstructor
@AllArgsConstructor
public class Owner extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String storeName;

    public Owner(String name, String email, String password, String storeName) {
        this.name = name;
        this.email = email;
        this.password = BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, password.toCharArray());
        this.storeName = storeName;
    }
}
