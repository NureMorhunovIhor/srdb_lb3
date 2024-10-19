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

    // Получить пассажира по ID для редактирования
    @GetMapping("/edit/{id}")
    @ResponseBody
    public ResponseEntity<Passenger> editPassenger(@PathVariable Integer id) {
        Optional<Passenger> passenger = passengerRepository.findById(id);
        return passenger.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Сохранить или обновить пассажира
    @PostMapping("/save")
    public String savePassenger(@ModelAttribute Passenger passenger) {
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