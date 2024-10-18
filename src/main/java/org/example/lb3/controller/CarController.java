package org.example.lb3.controller;

import org.example.lb3.dto.CarCategoryDTO;
import org.example.lb3.entity.Car;
import org.example.lb3.entity.CarCategory; // Убедитесь, что у вас есть этот класс
import org.example.lb3.repository.CarCategoryRepository; // Добавьте этот репозиторий
import org.example.lb3.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarRepository carRepository;
    private final CarCategoryRepository carCategoryRepository; // Добавьте этот репозиторий

    @Autowired
    public CarController(CarRepository carRepository, CarCategoryRepository carCategoryRepository) {
        this.carRepository = carRepository;
        this.carCategoryRepository = carCategoryRepository; // Инициализация
    }

    // Получить все машины с категориями
    @GetMapping("/all")
    public String getAllCarsWithCategory(Model model) {
        List<Object[]> carData = carRepository.findAllCarsWithCategory();
        List<CarCategoryDTO> cars = new ArrayList<>();

        for (Object[] obj : carData) {
            cars.add(new CarCategoryDTO(
                    (String) obj[0],     // Car_number
                    (String) obj[1],     // Model
                    (String) obj[2],     // Color
                    (Integer) obj[3],    // Production_year
                    (String) obj[4],     // Category_name
                    obj[5] != null ? (Integer) obj[5] : 0,  // Max_passengers_number
                    obj[6] != null ? (Double) obj[6] : 0.0  // Kilometer_price
            ));
        }
        model.addAttribute("cars", cars);
        return "carDetails"; // HTML-шаблон для отображения
    }

    // Удаление машины по номеру
    @GetMapping("/delete/{carNumber}")
    public String deleteCar(@PathVariable String carNumber) {
        carRepository.deleteById(carNumber);
        return "redirect:/cars/all"; // Перенаправление на список после удаления
    }

    @GetMapping("/edit/{carNumber}")
    public String showEditForm(@PathVariable String carNumber, Model model) {
        Optional<Car> carOptional = carRepository.findById(carNumber);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            model.addAttribute("car", car);

            // Здесь нужно получить список доступных категорий
            List<CarCategory> categories = carCategoryRepository.findAll(); // Замените на ваш репозиторий
            model.addAttribute("categories", categories);

            return "editCar"; // Отдельный HTML-шаблон для редактирования
        }
        return "redirect:/cars/all"; // Если машина не найдена, перенаправление на список
    }

    @PostMapping("/update")
    public String updateCar(@ModelAttribute Car updatedCar) {
        Optional<Car> existingCarOptional = carRepository.findById(updatedCar.getCarNumber());

        if (existingCarOptional.isPresent()) {
            Car existingCar = existingCarOptional.get();

            // Обновление данных автомобиля
            existingCar.setModel(updatedCar.getModel());
            existingCar.setColor(updatedCar.getColor());
            existingCar.setProductionYear(updatedCar.getProductionYear());

            // Если категория изменилась, обновляем ее
            if (updatedCar.getCarCategory() != null) {
                existingCar.setCarCategory(updatedCar.getCarCategory());
            }

            // Сохраняем изменения
            carRepository.save(existingCar);
        }

        return "redirect:/cars/all";
    }
}
