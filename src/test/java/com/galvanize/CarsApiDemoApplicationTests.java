package com.galvanize;

import com.galvanize.cars.Car;
import com.galvanize.cars.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarsApiDemoApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository.save(new Car("prius", "hybrid"));
    }

    @Test
    void contextLoads() {
    }

//    Integration Tests
//    Given: When: GET request to /cars, then:
//      - all cars in system are returned
//    Given: When: GET: /cars/{name} Then:
//      - if exists, returns the car details
    @Test
    void getAllCars() {
        ResponseEntity<Car> response = restTemplate.getForEntity("/cars/prius", Car.class);
        //From AssertJ Library - Provided by @SpringBootTest
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("prius");
        assertThat(response.getBody().getType()).isEqualTo("hybrid");
    }
//      - otherwise, returns 204 no content
//    Given: When: POST: /cars Body: {"name": "prius", "type": "hybrid"} Then:
//      - Saves the car, and returns the details

}
