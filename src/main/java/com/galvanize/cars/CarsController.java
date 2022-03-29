package com.galvanize.cars;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarsController {

    CarService service;

    public CarsController(CarService service) {
        this.service = service;
    }

    @GetMapping("/cars")
    public List<Car> getAllCars(){
        return service.getAllCars();
    }

    @GetMapping("/cars/{name}")
    public ResponseEntity<Car> getOneCar(@PathVariable String name){
        Car car;
        try{
            car = service.getCarDetails(name);
        }catch (CarNotFoundException e){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(car);
    }

    @PostMapping("/cars")
    public Car addCar(@RequestBody Car car){
        return service.addCar(car.getName(), car.getType());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void carNotFoundHandle(CarNotFoundException e){
    }
}
