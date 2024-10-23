package org.example.lb3.controller;

import org.example.lb3.entity.Driver;
import org.example.lb3.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    private DriverRepository driverRepository;

    // Получение всех водителей
    @GetMapping
    public List<Driver> getAllDrivers() {
        return (List<Driver>) driverRepository.findAll();
    }

    // Получение водителя по ID
    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable("id") Integer id) {
        return driverRepository.findById(id)
                .map(driver -> ResponseEntity.ok(driver))
                .orElse(ResponseEntity.notFound().build());
    }

    // Сохранение или обновление водителя
    @PostMapping
    public Driver saveDriver(@RequestBody Driver driver) {
        return driverRepository.save(driver);
    }

    // Обновление водителя
    @PutMapping("/{id}")
    public ResponseEntity<Driver> updateDriver(@PathVariable("id") Integer id, @RequestBody Driver driver) {
        if (!driverRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        driver.setId(id); // Убедитесь, что ID у драйвера установлен
        Driver updatedDriver = driverRepository.save(driver);
        return ResponseEntity.ok(updatedDriver);
    }

    // Удаление водителя
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable("id") Integer id) {
        if (driverRepository.existsById(id)) {
            driverRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Получение количества водителей по городу
    @GetMapping("/count")
    public ResponseEntity<Integer> getDriverCountByCity(@RequestParam("city") String city) {
        Integer count = driverRepository.countByCity(city);
        return ResponseEntity.ok(count);
    }

}
