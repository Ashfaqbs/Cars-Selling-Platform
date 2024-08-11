package com.ashfaq.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ashfaq.application.model.Car;
import com.ashfaq.application.repository.CarRepository;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car updateCar(Long id, Car car) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
        
        existingCar.setModel(car.getModel());
        existingCar.setBrand(car.getBrand());
        existingCar.setPrice(car.getPrice());
        
        return carRepository.save(existingCar);
    }

    @Override
    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}
