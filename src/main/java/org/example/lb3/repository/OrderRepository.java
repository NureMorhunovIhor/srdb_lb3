package org.example.lb3.repository;

import org.example.lb3.entity.Order;
import org.example.lb3.dto.MostExpensiveOrderOnDateDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query(value = "SELECT Orders_id AS ordersId, Adress_from AS adressFrom, Adress_to AS adressTo, " +
            "Price AS price, Creation_datetime AS creationDatetime " +
            "FROM GetMostExpensiveOrderOnDate(:orderDate)", nativeQuery = true)
    List<MostExpensiveOrderOnDateDTO> findMostExpensiveOrderOnDate(@Param("orderDate") String orderDate);
}
