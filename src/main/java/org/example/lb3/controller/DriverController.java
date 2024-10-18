package org.example.lb3.controller;

import org.example.lb3.entity.Driver;
import org.example.lb3.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class DriverController {

    @Autowired
    private DriverRepository driverRepository;

    // Получение всех водителей
    @GetMapping("/drivers")
    public String getAllDrivers(Model model) {
        List<Driver> drivers = (List<Driver>) driverRepository.findAll();
        model.addAttribute("drivers", drivers);
        return "driver-list"; // имя шаблона
    }

    // Отображение формы для редактирования водителя
    @GetMapping("/drivers/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Optional<Driver> driver = driverRepository.findById(id);
        if (driver.isPresent()) {
            model.addAttribute("driver", driver.get());
            return "edit-driver"; // шаблон формы для редактирования
        } else {
            // если водитель не найден
            return "redirect:/drivers";
        }
    }

    // Сохранение изменений водителя
    @PostMapping("/drivers/update/{id}")
    public String updateDriver(@PathVariable("id") Long id, @ModelAttribute("driver") Driver driver) {
        driverRepository.save(driver); // обновляем водителя
        return "redirect:/drivers"; // перенаправляем на список водителей
    }

    // Удаление водителя
    @GetMapping("/drivers/delete/{id}")
    public String deleteDriver(@PathVariable("id") Integer id) {
        driverRepository.deleteById(id); // удаляем водителя по ID
        return "redirect:/drivers"; // перенаправляем на список водителей
    }
}
