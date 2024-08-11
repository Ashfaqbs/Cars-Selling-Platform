package com.ashfaq.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashfaq.application.model.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}
