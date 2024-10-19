package org.example.lb3.controller;

import org.example.lb3.entity.Order;
import org.example.lb3.entity.Review;
import org.example.lb3.repository.OrderRepository;
import org.example.lb3.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public ReviewController(ReviewRepository reviewRepository, OrderRepository orderRepository) {
        this.reviewRepository = reviewRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public String getReviews(Model model) {
        List<Review> reviews = (List<Review>) reviewRepository.findAll();
        List<Order> orders = (List<Order>) orderRepository.findAll();
        model.addAttribute("reviews", reviews);
        model.addAttribute("orders", orders);
        return "reviews-list";
    }


    @GetMapping("/add")
    public String addReviewForm(Model model) {
        model.addAttribute("review", new Review());
        model.addAttribute("orders", orderRepository.findAll());
        return "review-form";
    }

    @PostMapping("/add")
    public String addReview(@ModelAttribute Review review, @RequestParam Integer orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            review.setOrder(order.get());
            review.setCreationDatetime(LocalDateTime.now()); // Устанавливаем текущее время
        } else {
        }
        reviewRepository.save(review);
        return "redirect:/reviews";
    }



    @GetMapping("/edit/{id}")
    @ResponseBody // Возвращаем объект в формате JSON
    public Review getReviewJson(@PathVariable Integer id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @PostMapping("/edit/{id}")
    public String editReview(@PathVariable Integer id, @ModelAttribute Review review) {
        Review existingReview = reviewRepository.findById(id).orElse(null);
        if (existingReview != null) {
            review.setCreationDatetime(LocalDateTime.now()); // Устанавливаем текущую дату и время
            review.setId(id);
            reviewRepository.save(review);
        }
        return "redirect:/reviews";
    }


    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable Integer id) {
        reviewRepository.deleteById(id);
        return "redirect:/reviews";
    }
}
