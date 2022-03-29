package com.galvanize.cars;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    CarRepository repository;

    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    public List<Car> getAllCars() {
        return repository.findAll();
    }

    public Car getCarDetails(String name) {
        Optional<Car> carOptional = repository.findCarByName(name);
//        if (carOptional.isPresent()) {
//            return carOptional.get();
//        } else {
//            throw new CarNotFoundException();
//        }

        return carOptional.orElseThrow(() -> new CarNotFoundException());
    }

    public Car addCar(String name, String type) {
        return repository.save(new Car(name, type));
    }
}
