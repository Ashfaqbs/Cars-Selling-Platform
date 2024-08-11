package com.ashfaq.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashfaq.application.model.ApplicationUser;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

	Optional<ApplicationUser> findByUsername(String applicationUsername);
}