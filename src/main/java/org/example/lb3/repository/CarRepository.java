package org.example.lb3.repository;

import org.example.lb3.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {

    @Query(value = "SELECT c.Car_number, c.Model, c.Color, c.Production_year, " +
            "cc.Category_name, cc.Max_passengers_number, cc.Kilometer_price, d.Driver_id AS driver_id " + // Измените на правильное имя
            "FROM Car c " +
            "LEFT JOIN Car_category cc ON c.Car_category_id = cc.Car_category_id " +
            "LEFT JOIN Driver d ON c.Driver_id = d.Driver_id",
            nativeQuery = true)
    List<Object[]> findAllCarsWithCategory();
}


