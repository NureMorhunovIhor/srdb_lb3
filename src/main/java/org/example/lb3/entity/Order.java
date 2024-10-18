package org.example.lb3.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Orders_id", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "Adress_from", nullable = false, length = 100)
    private String adressFrom;

    @Nationalized
    @Column(name = "Adress_to", nullable = false, length = 100)
    private String adressTo;

    @ColumnDefault("getdate()")
    @Column(name = "Creation_datetime")
    private Instant creationDatetime;

    @Column(name = "Price", nullable = false)
    private Double price;

    @Nationalized
    @Column(name = "Order_state", nullable = false, length = 40)
    private String orderState;

    @Column(name = "Luggage_weight")
    private Double luggageWeight;

    @ColumnDefault("getdate()")
    @Column(name = "Preferred_datetime")
    private Instant preferredDatetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "Driver_id")
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Passenger_id")
    private Passenger passenger;

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
    @Nationalized
    @Column(name = "Description", length = 100)
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdressFrom() {
        return adressFrom;
    }

    public void setAdressFrom(String adressFrom) {
        this.adressFrom = adressFrom;
    }

    public String getAdressTo() {
        return adressTo;
    }

    public void setAdressTo(String adressTo) {
        this.adressTo = adressTo;
    }

    public Instant getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(Instant creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public Double getLuggageWeight() {
        return luggageWeight;
    }

    public void setLuggageWeight(Double luggageWeight) {
        this.luggageWeight = luggageWeight;
    }

    public Instant getPreferredDatetime() {
        return preferredDatetime;
    }

    public void setPreferredDatetime(Instant preferredDatetime) {
        this.preferredDatetime = preferredDatetime;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}