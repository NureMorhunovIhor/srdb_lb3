package org.example.lb3.controller;

import org.example.lb3.entity.Driver;
import org.example.lb3.entity.Order;
import org.example.lb3.entity.Passenger;
import org.example.lb3.repository.DriverRepository;
import org.example.lb3.repository.OrderRepository;
import org.example.lb3.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final DriverRepository driverRepository;
    private final PassengerRepository passengerRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository,
                           DriverRepository driverRepository,
                           PassengerRepository passengerRepository) {
        this.orderRepository = orderRepository;
        this.driverRepository = driverRepository;
        this.passengerRepository = passengerRepository;
    }

    @GetMapping
    public String getOrders(Model model) {
        List<Order> orders = (List<Order>) orderRepository.findAll();
        model.addAttribute("orders", orders);
        model.addAttribute("drivers", driverRepository.findAll());
        model.addAttribute("passengers", passengerRepository.findAll());
        return "order-list";
    }

    @GetMapping("/add")
    public String addOrderForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("drivers", driverRepository.findAll());
        model.addAttribute("passengers", passengerRepository.findAll());
        return "order-form";
    }

    @PostMapping("/add")
    public String addOrder(@ModelAttribute Order order,
                           @RequestParam Integer driverId,
                           @RequestParam Integer passengerId) {
        Optional<Driver> driver = driverRepository.findById(driverId);
        Optional<Passenger> passenger = passengerRepository.findById(passengerId);

        driver.ifPresent(order::setDriver);
        passenger.ifPresent(order::setPassenger);

        order.setCreationDatetime(LocalDateTime.now());
        orderRepository.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    @ResponseBody
    public Order getOrderJson(@PathVariable Integer id, Model model) {
       return orderRepository.findById(id).orElse(null);
    }

    @PostMapping("/edit/{id}")
    public String editOrder(@PathVariable Integer id,
                            @ModelAttribute Order order,
                            @RequestParam Integer driverId,
                            @RequestParam Integer passengerId) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        Optional<Driver> driver = driverRepository.findById(driverId);
        Optional<Passenger> passenger = passengerRepository.findById(passengerId);

        if (existingOrder.isPresent()) {
            order.setId(id);
            driver.ifPresent(order::setDriver);
            passenger.ifPresent(order::setPassenger);
            order.setCreationDatetime(LocalDateTime.now());
            orderRepository.save(order);
        }
        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Integer id) {
        orderRepository.deleteById(id);
        return "redirect:/orders";
    }
}