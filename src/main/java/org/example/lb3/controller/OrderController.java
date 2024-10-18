package org.example.lb3.controller;

import org.example.lb3.entity.Order;
import org.example.lb3.entity.Driver;
import org.example.lb3.entity.Passenger;
import org.example.lb3.repository.OrderRepository;
import org.example.lb3.repository.DriverRepository;
import org.example.lb3.repository.PassengerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {
    private final OrderRepository orderRepository;
    private final DriverRepository driverRepository;
    private final PassengerRepository passengerRepository;

    public OrderController(OrderRepository orderRepository, DriverRepository driverRepository, PassengerRepository passengerRepository) {
        this.orderRepository = orderRepository;
        this.driverRepository = driverRepository;
        this.passengerRepository = passengerRepository;
    }

    // List all orders
    @GetMapping("/orders")
    public String listOrders(Model model) {
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "order-list";
    }

    // Get details of a specific order
    @GetMapping("/orders/{id}")
    public String getOrderDetails(@PathVariable Integer id, Model model) {
        Order order = orderRepository.findById(id).orElse(null);
        model.addAttribute("order", order);
        return "order-details";
    }

    // Show form to edit an existing order
    @GetMapping("/orders/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Order order = orderRepository.findById(id).orElse(null);
        List<Driver> drivers = (List<Driver>) driverRepository.findAll();
        List<Passenger> passengers = passengerRepository.findAll();

        if (order != null) {
            model.addAttribute("order", order);
            model.addAttribute("drivers", drivers); // List of available drivers
            model.addAttribute("passengers", passengers); // List of available passengers
            return "edit-order";
        }
        return "redirect:/orders"; // If order not found, redirect to order list
    }

    // Update an existing order
    @PostMapping("/orders/update/{id}")
    public String updateOrder(@PathVariable Integer id, @ModelAttribute Order orderDetails,
                              @RequestParam("driverId") Integer driverId,
                              @RequestParam("passengerId") Integer passengerId) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            // Update the fields as needed
            order.setAdressFrom(orderDetails.getAdressFrom());
            order.setAdressTo(orderDetails.getAdressTo());
            order.setPrice(orderDetails.getPrice());
            order.setOrderState(orderDetails.getOrderState());
            order.setDescription(orderDetails.getDescription());

            // Find and set the new driver
            Driver driver = driverRepository.findById(driverId).orElse(null);
            if (driver != null) {
                order.setDriver(driver);
            }

            // Find and set the new passenger
            Passenger passenger = passengerRepository.findById(passengerId).orElse(null);
            if (passenger != null) {
                order.setPassenger(passenger);
            }

            // Save updated order to the database
            orderRepository.save(order);
        }
        return "redirect:/orders";
    }

    // Delete an order by ID
    @GetMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable Integer id) {
        orderRepository.deleteById(id);
        return "redirect:/orders";
    }
}
