package org.example.lb3.repository;

import org.example.lb3.entity.CarCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarCategoryRepository extends JpaRepository<CarCategory, Integer> {
    @Query(value = "SELECT dbo.GetDriversCountByCity(:city)", nativeQuery = true)
    Integer countByCity(@Param("city") String city);
}
