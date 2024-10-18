package org.example.lb3.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Car {

  @Id
  @Column(name = "Car_number", nullable = false, length = 8)
  private String carNumber;

  @Column(name = "Model", nullable = false, length = 50)
  private String model;

  @Column(name = "Color", nullable = false, length = 50)
  private String color;

  @Column(name = "Production_year", nullable = false)
  private int productionYear;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Car_category_id", referencedColumnName = "Car_category_id")
  @OnDelete(action = OnDeleteAction.SET_NULL)
  private CarCategory carCategory;

  // Getters and setters
  public String getCarNumber() {
    return carNumber;
  }

  public void setCarNumber(String carNumber) {
    this.carNumber = carNumber;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public int getProductionYear() {
    return productionYear;
  }

  public void setProductionYear(int productionYear) {
    this.productionYear = productionYear;
  }

  public CarCategory getCarCategory() {
    return carCategory;
  }

  public void setCarCategory(CarCategory carCategory) {
    this.carCategory = carCategory;
  }
}
