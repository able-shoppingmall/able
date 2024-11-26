package com.sparta.able.repository;

import com.sparta.able.entity.Owner;

import jakarta.validation.constraints.Email;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    boolean existsByEmail(@Email String email);
    Optional<Owner> findByEmail(String email);

    Optional<Owner> findByName(String email);
}
