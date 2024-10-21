package org.example.lb3.controller;

import org.example.lb3.entity.Car;
import org.example.lb3.entity.CarCategory;
import org.example.lb3.entity.Driver;
import org.example.lb3.repository.CarCategoryRepository;
import org.example.lb3.repository.CarRepository;
import org.example.lb3.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cars")
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

    // Получить все машины
    @GetMapping("/all")
    public String getAllCars(Model model) {
        List<Car> cars = carRepository.findAll();
        List<Driver> drivers = (List<Driver>) driverRepository.findAll();
        List<CarCategory> carCategories = carCategoryRepository.findAll();

        // Логируем данные
        System.out.println("Cars: " + cars);
        System.out.println("Drivers: " + drivers);
        System.out.println("Car Categories: " + carCategories);

        model.addAttribute("cars", cars);
        model.addAttribute("drivers", drivers);
        model.addAttribute("carCategories", carCategories);
        return "carDetails";
    }

    @PostMapping("/add")
    public String addCar(@RequestParam Integer driverId, @RequestParam Integer carCategoryId, @ModelAttribute Car newCar) {
        Optional<Driver> driverOptional = driverRepository.findById(driverId);
        Optional<CarCategory> carCategoryOptional = carCategoryRepository.findById(carCategoryId);

        driverOptional.ifPresent(newCar::setDriver);
        carCategoryOptional.ifPresent(newCar::setCarCategory);

        // Сохраняем новый автомобиль
        carRepository.save(newCar);
        return "redirect:/cars/all";
    }



    // Удаление машины по номеру
    @GetMapping("/delete/{carNumber}")
    public String deleteCar(@PathVariable String carNumber) {
        carRepository.deleteById(carNumber);
        return "redirect:/cars/all";
    }

    @GetMapping("/edit/{carNumber}")
    @ResponseBody
    public ResponseEntity<?> showEditForm(@PathVariable String carNumber) {
        System.out.println("Received request for car number: " + carNumber);
        Optional<Car> carOptional = Optional.of(carRepository.findById(carNumber).get());
        System.out.println("Car found: " + carOptional.get());
        return ResponseEntity.ok(carOptional.get());
    }



    @PostMapping("/edit/{carNumber}")
    public String updateCar(@ModelAttribute Car updatedCar, @RequestParam Integer driverId, @RequestParam Integer carCategoryId) {
        Optional<Car> existingCarOptional = carRepository.findById(updatedCar.getCarNumber());

        if (existingCarOptional.isPresent()) {
            Car existingCar = existingCarOptional.get();
            Optional<Driver> driverOptional = driverRepository.findById(driverId);
            Optional<CarCategory> carCategoryOptional = carCategoryRepository.findById(carCategoryId);

            existingCar.setModel(updatedCar.getModel());
            existingCar.setColor(updatedCar.getColor());
            existingCar.setProductionYear(updatedCar.getProductionYear());
            driverOptional.ifPresent(existingCar::setDriver);
            carCategoryOptional.ifPresent(existingCar::setCarCategory);
            carRepository.save(existingCar);
        }

        return "redirect:/cars/all";
    }
}
