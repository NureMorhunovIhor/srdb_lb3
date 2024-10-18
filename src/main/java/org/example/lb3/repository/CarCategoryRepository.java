package org.example.lb3.repository;

import org.example.lb3.entity.CarCategory; // Импортируйте вашу сущность CarCategory
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarCategoryRepository extends JpaRepository<CarCategory, Integer> { // Замените Integer на тип ID вашей категории


}
