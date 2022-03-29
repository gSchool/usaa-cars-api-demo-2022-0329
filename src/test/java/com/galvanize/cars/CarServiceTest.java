package com.galvanize.cars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    CarRepository carRepository;

    CarService carService;

    @BeforeEach
    void setUp() {
        carService = new CarService(carRepository);
    }

    @Test
    void getAllCars_returnsList() {
        when(carRepository.findAll()).thenReturn(Arrays.asList(new Car("prius", "hybrid")));
        List<Car> cars = carService.getAllCars();
        assertNotNull(cars);
        assertEquals(1, cars.size());
    }

    @Test
    void getCarDetails_exists_returnsCar() {
        when(carRepository.findCarByName(anyString())).thenReturn(Optional.of(new Car("prius", "hybrid")));
        Car actualCar = carService.getCarDetails("prius");
        assertNotNull(actualCar);
        assertEquals(actualCar.getName(), "prius");
    }

    @Test
    void getCarDetails_notExists_throwCarNotFound() {
        when(carRepository.findCarByName(anyString())).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> {
            carService.getCarDetails(anyString());
        });
    }

    @Test
    void addCar() {
        Car car = new Car("prius", "hybrid");
        when(carRepository.save(any(Car.class))).thenReturn(car);
        assertNotNull(carService.addCar("prius", "hybrid"));
    }
}