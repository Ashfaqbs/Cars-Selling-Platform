package com.ashfaq.application.service;

import java.util.List;

import com.ashfaq.application.model.Car;

public interface CarService {

    Car saveCar(Car car);

    Car updateCar(Long id, Car car);

    Car getCarById(Long id);

    List<Car> getAllCars();

    void deleteCar(Long id);
}
