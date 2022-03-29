package com.galvanize;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CarsApiDemoApplicationTests {

    @Test
    void contextLoads() {
    }

//    Given: When: GET request to /cars, then all cars in system are returned
//    Given: When: GET: /cars/{name} Then:
//        - if exists, returns the car details
//        - otherwise, returns 204 no content
//    Given: When: POST: /cars Body: {"name": "prius"} Then:
//    - Saves the car, and returns the details
}
