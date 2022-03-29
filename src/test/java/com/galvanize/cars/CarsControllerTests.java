package com.galvanize.cars;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarsController.class)
public class CarsControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CarService carService;

//    GET: /cars returns all cars
    @Test
    void getAllCars_returnsList() throws Exception {
        // Arrange
        List<Car> cars = Arrays.asList(new Car("prius", "hybrid"));
        when(carService.getAllCars()).thenReturn(cars);
        // Act
        // Make a call to the API
        mockMvc.perform(get("/cars"))
        // Assert
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

//    GET: /cars/{name}
//        - if exists, returns the car details
    @Test
    void getOneCar_exists_returnsCar() throws Exception {
        Car car = new Car("prius", "hybrid");
        when(carService.getCarDetails("prius")).thenReturn(car);
        mockMvc.perform(get("/cars/prius"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("prius"))
                .andExpect(jsonPath("type").value("hybrid"));
    }

//        - otherwise, returns 204 no content

    @Test
    void getOneCar_notExists_returnsNoContent() throws Exception {
        Car car = new Car("prius", "hybrid");
        when(carService.getCarDetails("prius")).thenThrow(new CarNotFoundException());
        mockMvc.perform(get("/cars/prius"))
                .andExpect(status().isNoContent());
    }

//    POST: /cars Body: Car name and type
//    - Saves the car, and returns the details

    @Test
    void addCar_returnsCar() throws Exception {
        Car car = new Car("prius", "hybrid");
        when(carService.addCar("prius", "hybrid")).thenReturn(car);
        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"prius\", \"type\": \"hybrid\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("prius"))
                .andExpect(jsonPath("type").value("hybrid"));
    }
}
