package org.example.lb3.controller;

import org.example.lb3.entity.Passenger;
import org.example.lb3.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerRepository passengerRepository;

    @Autowired
    public PassengerController(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    // Получить всех пассажиров и отобразить их в представлении
    @GetMapping
    public String getAllPassengers(Model model) {
        List<Passenger> passengers = passengerRepository.findAll();
        model.addAttribute("passengers", passengers);
        return "passengers"; // название вашего HTML файла (passengers.html)
    }

    // Создать нового пассажира
    @PostMapping("/add")
    public String createPassenger(@ModelAttribute Passenger passenger) {
        passengerRepository.save(passenger);
        return "redirect:/passengers"; // перенаправление на страницу списка пассажиров
    }

    // Получить пассажира по ID для редактирования
    @GetMapping("/edit/{id}")
    public String editPassenger(@PathVariable Integer id, Model model) {
        Optional<Passenger> passenger = passengerRepository.findById(id);
        if (passenger.isPresent()) {
            model.addAttribute("passenger", passenger.get());
            return "editPassenger"; // имя HTML шаблона для редактирования
        } else {
            return "redirect:/passengers"; // перенаправление, если не найдено
        }
    }

    // Обновить существующего пассажира
    @PostMapping("/edit")
    public String updatePassenger(@ModelAttribute Passenger passenger) {
        passengerRepository.save(passenger);
        return "redirect:/passengers"; // перенаправление на страницу списка пассажиров
    }

    // Удалить пассажира
    @GetMapping("/delete/{id}")
    public String deletePassenger(@PathVariable Integer id) {
        if (passengerRepository.existsById(id)) {
            passengerRepository.deleteById(id);
        }
        return "redirect:/passengers"; // перенаправление на страницу списка пассажиров
    }
}
