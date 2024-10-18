package org.example.lb3.repository;

import org.example.lb3.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

    Passenger findByPhoneNumber(String phoneNumber);
}
