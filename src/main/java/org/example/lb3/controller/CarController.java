package org.example.lb3.controller;

import org.example.lb3.entity.Car;
import org.example.lb3.entity.CarCategory;
import org.example.lb3.entity.Driver;
import org.example.lb3.exception.ErrorResponse;
import org.example.lb3.repository.CarCategoryRepository;
import org.example.lb3.repository.CarRepository;
import org.example.lb3.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.lb3.exception.CarAlreadyExistsException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarRepository carRepository;
    private final DriverRepository driverRepository;
    private final CarCategoryRepository carCategoryRepository;

    @Autowired
    public CarController(CarRepository carRepository, DriverRepository driverRepository, CarCategoryRepository carCategoryRepository) {
        this.carRepository = carRepository;
        this.driverRepository = driverRepository;
        this.carCategoryRepository = carCategoryRepository;
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @GetMapping("/drivers")
    public List<Driver> getAllDrivers() {
        return (List<Driver>) driverRepository.findAll();
    }

    @GetMapping("/categories")
    public List<CarCategory> getAllCategories() {
        return carCategoryRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@RequestBody Car newCar) {
        if (carRepository.existsById(newCar.getCarNumber())) {
            throw new CarAlreadyExistsException(newCar.getCarNumber());
        }

        // Check if driver is provided, and set it if it exists
        if (newCar.getDriver() != null && newCar.getDriver().getId() != null) {
            Optional<Driver> driverOptional = driverRepository.findById(newCar.getDriver().getId());
            if (driverOptional.isPresent()) {
                newCar.setDriver(driverOptional.get());
            } else {
                return ResponseEntity.badRequest().body(null); // Driver not found
            }
        } else {
            newCar.setDriver(null); // Set driver to null if not provided
        }

        // Check if car category is provided and exists
        if (newCar.getCarCategory() == null || newCar.getCarCategory().getId() == null ||
                !carCategoryRepository.existsById(newCar.getCarCategory().getId())) {
            return ResponseEntity.badRequest().body(null); // Category not found or missing
        }

        newCar.setCarCategory(carCategoryRepository.findById(newCar.getCarCategory().getId()).get());

        Car savedCar = carRepository.save(newCar);
        return ResponseEntity.ok(savedCar);

    }

    @DeleteMapping("/{carNumber}")
    public ResponseEntity<Void> deleteCar(@PathVariable String carNumber) {
        if (!carRepository.existsById(carNumber)) {
            return ResponseEntity.notFound().build(); // Если машина не найдена
        }
        carRepository.deleteById(carNumber);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{carNumber}")
    public ResponseEntity<Car> getCar(@PathVariable String carNumber) {
        return carRepository.findById(carNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{carNumber}")
    public ResponseEntity<Car> updateCar(@PathVariable String carNumber, @RequestBody Car updatedCar) {
        if (!carRepository.existsById(carNumber)) {
            return ResponseEntity.notFound().build();
        }

        Optional<Car> existingCarOptional = carRepository.findById(carNumber);
        if (!existingCarOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Car existingCar = existingCarOptional.get();

        // Check if driver is provided in the update
        if (updatedCar.getDriver() != null && updatedCar.getDriver().getId() != null) {
            Optional<Driver> driverOpt = driverRepository.findById(updatedCar.getDriver().getId());
            if (driverOpt.isPresent()) {
                existingCar.setDriver(driverOpt.get());
            } else {
                return ResponseEntity.badRequest().body(null); // Водитель не найден
            }
        } else {
            existingCar.setDriver(null); // Set driver to null if not provided in update
        }

        if (updatedCar.getCarCategory() != null && updatedCar.getCarCategory().getId() != null) {
            Optional<CarCategory> categoryOpt = carCategoryRepository.findById(updatedCar.getCarCategory().getId());
            if (categoryOpt.isPresent()) {
                existingCar.setCarCategory(categoryOpt.get());
            } else {
                return ResponseEntity.badRequest().body(null); // Категория не найдена
            }
        }

        existingCar.setModel(updatedCar.getModel());
        existingCar.setColor(updatedCar.getColor());
        existingCar.setProductionYear(updatedCar.getProductionYear());

        Car savedCar = carRepository.save(existingCar);
        return ResponseEntity.ok(savedCar);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCarUsingProcedure(@RequestParam String model,
                                                       @RequestParam Integer productionYear,
                                                       @RequestParam String categoryName,
                                                       @RequestParam(defaultValue = "Black") String color,
                                                       @RequestParam(required = false) String carNumber) {
        try {
            String generatedCarNumber = carRepository.addCar(model, productionYear, categoryName, color, carNumber);
            return ResponseEntity.ok("Car added successfully with number: " + generatedCarNumber);
        } catch (InvalidDataAccessResourceUsageException ex) {
            Throwable cause = ex.getRootCause();
            if (cause instanceof SQLException sqlException) {
                System.out.println("SQL Error Code: " + sqlException.getErrorCode());
                System.out.println("SQL State: " + sqlException.getSQLState());
                System.out.println("Error Message: " + sqlException.getMessage());

                if (sqlException.getErrorCode() == 50000) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Car with number already exists.");
                }
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the car.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the car.");
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Car>> getCarsByCategory(@PathVariable Integer categoryId) {
        List<Car> cars = carRepository.findByCarCategory_Id(categoryId);
        return ResponseEntity.ok(cars);
    }
}
