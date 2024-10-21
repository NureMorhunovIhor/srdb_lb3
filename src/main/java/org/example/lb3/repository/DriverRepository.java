package org.example.lb3.repository;

import org.example.lb3.entity.Driver;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DriverRepository extends CrudRepository<Driver, Integer> {
    @Query(value = "SELECT dbo.GetDriversCountByCity(:city)", nativeQuery = true)
    int countByCity(@Param("city") String city);
}
