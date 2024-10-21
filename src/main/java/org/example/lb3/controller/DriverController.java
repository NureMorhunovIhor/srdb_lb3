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
        return "driver-list";
    }

    @GetMapping("/drivers/{id}")
    @ResponseBody
    public Optional<Driver> getDriverById(@PathVariable("id") Integer id) {
        return driverRepository.findById(id);
    }

    @PostMapping("/drivers/save")
    public String saveDriver(@ModelAttribute("driver") Driver driver) {
        driverRepository.save(driver);
        return "redirect:/drivers";
    }

    // Удаление водителя
    @GetMapping("/drivers/delete/{id}")
    public String deleteDriver(@PathVariable("id") Integer id) {
        driverRepository.deleteById(id);
        return "redirect:/drivers";
    }
}
