package org.example.lb3.controller;

import org.example.lb3.entity.CarCategory;
import org.example.lb3.repository.CarCategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CarCategoryController {

    private final CarCategoryRepository carCategoryRepository;

    public CarCategoryController(CarCategoryRepository carCategoryRepository) {
        this.carCategoryRepository = carCategoryRepository;
    }

    @GetMapping
    public String showCategories(Model model) {
        model.addAttribute("carCategories", carCategoryRepository.findAll());
        return "categories";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute CarCategory category) {
        carCategoryRepository.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    @ResponseBody
    public CarCategory getCategory(@PathVariable Integer id) {
        return carCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Integer id) {
        carCategoryRepository.deleteById(id);
        return "redirect:/categories";
    }
}

