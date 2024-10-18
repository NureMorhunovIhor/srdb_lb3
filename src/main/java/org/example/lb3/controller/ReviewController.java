package org.example.lb3.controller;

import org.example.lb3.entity.Order;
import org.example.lb3.entity.Review;
import org.example.lb3.repository.OrderRepository;
import org.example.lb3.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("reviews", reviews);
        return "reviews-list"; // имя шаблона
    }

    @GetMapping("/add")
    public String addReviewForm(Model model) {
        model.addAttribute("review", new Review());
        model.addAttribute("orders", orderRepository.findAll());
        return "review-form"; // шаблон для добавления отзыва
    }

    @PostMapping("/add")
    public String addReview(@ModelAttribute Review review) {
        reviewRepository.save(review);
        return "redirect:/reviews";
    }

    @GetMapping("/edit/{id}")
    public String editReviewForm(@PathVariable Integer id, Model model) {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isPresent()) {
            model.addAttribute("review", review.get());
            model.addAttribute("orders", orderRepository.findAll());
            return "review-form";
        }
        return "redirect:/reviews";
    }

    @PostMapping("/edit/{id}")
    public String editReview(@PathVariable Integer id, @ModelAttribute Review review) {
        review.setId(id);
        reviewRepository.save(review);
        return "redirect:/reviews";
    }

    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable Integer id) {
        reviewRepository.deleteById(id);
        return "redirect:/reviews";
    }
}
