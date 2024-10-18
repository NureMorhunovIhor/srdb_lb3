package org.example.lb3.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "Car_category")
public class CarCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Car_category_id", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "Category_name", nullable = false, length = 15)
    private String categoryName;

    @Column(name = "Max_passengers_number", nullable = false)
    private Integer maxPassengersNumber;

    @Column(name = "Kilometer_price", nullable = false)
    private Double kilometerPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getMaxPassengersNumber() {
        return maxPassengersNumber;
    }

    public void setMaxPassengersNumber(Integer maxPassengersNumber) {
        this.maxPassengersNumber = maxPassengersNumber;
    }

    public Double getKilometerPrice() {
        return kilometerPrice;
    }

    public void setKilometerPrice(Double kilometerPrice) {
        this.kilometerPrice = kilometerPrice;
    }

}