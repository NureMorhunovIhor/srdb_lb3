package org.example.lb3.dto;

import java.time.LocalDateTime;

public interface MostExpensiveOrderOnDateDTO {
    Integer getOrdersId();
    String getAdressFrom();
    String getAdressTo();
    Double getPrice();
    LocalDateTime getCreationDatetime();
}
