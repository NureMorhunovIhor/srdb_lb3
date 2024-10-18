package org.example.lb3.dto;

public class CarCategoryDTO {
    private String carNumber;
    private String model;
    private String color;
    private int productionYear;
    private String categoryName;
    private int maxPassengersNumber;
    private double kilometerPrice;

    // Конструктор
    public CarCategoryDTO(String carNumber, String model, String color, int productionYear,
                          String categoryName, int maxPassengersNumber, double kilometerPrice) {
        this.carNumber = carNumber;
        this.model = model;
        this.color = color;
        this.productionYear = productionYear;
        this.categoryName = categoryName;
        this.maxPassengersNumber = maxPassengersNumber;
        this.kilometerPrice = kilometerPrice;
    }

    // Геттеры и сеттеры
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getMaxPassengersNumber() {
        return maxPassengersNumber;
    }

    public void setMaxPassengersNumber(int maxPassengersNumber) {
        this.maxPassengersNumber = maxPassengersNumber;
    }

    public double getKilometerPrice() {
        return kilometerPrice;
    }

    public void setKilometerPrice(double kilometerPrice) {
        this.kilometerPrice = kilometerPrice;
    }
}
