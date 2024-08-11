package com.ashfaq.application.controller;

import java.util.Base64;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ashfaq.application.model.Car;
import com.ashfaq.application.service.CarService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/sellers/cars")
public class CarCommandController {

    private final CarService carService;

//    @Autowired
    public CarCommandController(CarService carService) {
        this.carService = carService;
    }

    private String extractUsernameFromAuthHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            // Decode the Base64 encoded username:password
            String base64Credentials = authHeader.substring(6);
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            // Split the credentials to get the username
            return credentials.split(":")[0];
        }
        return null;
    }
    
    
    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car,@RequestHeader("Authorization") String authHeader) {
    	  String username = extractUsernameFromAuthHeader(authHeader);
          System.out.println("Username from Header: " + username);
          
          
          
//          OR 
          
       // Get the authenticated user
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          String usernamefromContext = authentication.getName();

          // Log the username
          log.info("User '{}' is creating a car", usernamefromContext);
          
          // logs 
//          Username from Header: seller
//          [2m2024-08-11T09:10:18.760+05:30[0;39m [32m INFO[0;39m [35m14252[0;39m [2m---[0;39m [2m[Sell-Cars-Application] [nio-8080-exec-3][0;39m [2m[0;39m[36mc.a.a.controller.CarCommandController   [0;39m [2m:[0;39m User 'seller' is creating a car
          
        Car savedCar = carService.saveCar(car);
        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car car) {
        Car updatedCar = carService.updateCar(id, car);
        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
