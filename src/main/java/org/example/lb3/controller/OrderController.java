package org.example.lb3.controller;

import org.example.lb3.entity.Driver;
import org.example.lb3.entity.Order;
import org.example.lb3.entity.Passenger;
import org.example.lb3.repository.DriverRepository;
import org.example.lb3.repository.OrderRepository;
import org.example.lb3.repository.PassengerRepository;
import org.example.lb3.dto.MostExpensiveOrderOnDateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final DriverRepository driverRepository;
    private final PassengerRepository passengerRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, DriverRepository driverRepository, PassengerRepository passengerRepository) {
        this.orderRepository = orderRepository;
        this.driverRepository = driverRepository;
        this.passengerRepository = passengerRepository;
    }

    @GetMapping
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Integer id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<Order> addOrder(@RequestBody Order newOrder) {
        Optional<Driver> driver = driverRepository.findById(newOrder.getDriver().getId());
        Optional<Passenger> passenger = passengerRepository.findById(newOrder.getPassenger().getId());

        if (driver.isPresent() && passenger.isPresent()) {
            newOrder.setDriver(driver.get());
            newOrder.setPassenger(passenger.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
        newOrder.setCreationDatetime(LocalDateTime.now());
        Order order = orderRepository.save(newOrder);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Integer id, @RequestBody Order updatedOrder) {
        if (!orderRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Optional<Order> existingOrderOptional = orderRepository.findById(id);
        if (existingOrderOptional.isPresent()) {
            Order existingOrder = existingOrderOptional.get();

            // Обновление водителя
            if (updatedOrder.getDriver() != null && updatedOrder.getDriver().getId() != null) {
                Optional<Driver> driverOpt = driverRepository.findById(updatedOrder.getDriver().getId());
                if (driverOpt.isPresent()) {
                    existingOrder.setDriver(driverOpt.get());
                } else {
                    return ResponseEntity.badRequest().body(null); // Водитель не найден
                }
            }

            // Обновление пассажира
            if (updatedOrder.getPassenger() != null && updatedOrder.getPassenger().getId() != null) {
                Optional<Passenger> passengerOpt = passengerRepository.findById(updatedOrder.getPassenger().getId());
                if (passengerOpt.isPresent()) {
                    existingOrder.setPassenger(passengerOpt.get());
                } else {
                    return ResponseEntity.badRequest().body(null);
                }
            }

            existingOrder.setAdressFrom(updatedOrder.getAdressFrom());
            existingOrder.setAdressTo(updatedOrder.getAdressTo());
            existingOrder.setPrice(updatedOrder.getPrice());
            existingOrder.setOrderState(updatedOrder.getOrderState());
            existingOrder.setLuggageWeight(updatedOrder.getLuggageWeight());
            existingOrder.setPreferredDatetime(updatedOrder.setCreationDatetime(LocalDateTime.now()));
            existingOrder.setDescription(updatedOrder.getDescription());

            // Сохраняем обновленный заказ
            Order savedOrder = orderRepository.save(existingOrder);
            return ResponseEntity.ok(savedOrder);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/most-expensive")
    public ResponseEntity<List<MostExpensiveOrderOnDateDTO>> getMostExpensiveOrderOnDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<MostExpensiveOrderOnDateDTO> result = orderRepository.findMostExpensiveOrderOnDate(date.toString());
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        if (!orderRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        orderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
