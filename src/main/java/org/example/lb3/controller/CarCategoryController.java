package org.example.lb3.controller;

import org.example.lb3.entity.CarCategory;
import org.example.lb3.repository.CarCategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CarCategoryController {

    private final CarCategoryRepository carCategoryRepository;

    public CarCategoryController(CarCategoryRepository carCategoryRepository) {
        this.carCategoryRepository = carCategoryRepository;
    }

    @GetMapping
    public List<CarCategory> getAllCategories() {
        return carCategoryRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<CarCategory> addCategory(@RequestBody CarCategory category) {
        CarCategory savedCategory = carCategoryRepository.save(category);
        return ResponseEntity.ok(savedCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarCategory> getCategory(@PathVariable Integer id) {
        return carCategoryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarCategory> updateCategory(@PathVariable Integer id, @RequestBody CarCategory category) {
        return carCategoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setCategoryName(category.getCategoryName());
                    existingCategory.setMaxPassengersNumber(category.getMaxPassengersNumber());
                    existingCategory.setKilometerPrice(category.getKilometerPrice());
                    CarCategory updatedCategory = carCategoryRepository.save(existingCategory);
                    return ResponseEntity.ok(updatedCategory);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        if (carCategoryRepository.existsById(id)) {
            carCategoryRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
