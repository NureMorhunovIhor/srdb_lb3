package org.example.lb3.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Driver_id", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "First_name", nullable = false, length = 40)
    private String firstName;

    @Nationalized
    @Column(name = "Last_name", nullable = false, length = 50)
    private String lastName;

    @Nationalized
    @Column(name = "Middle_name", nullable = false, length = 50)
    private String middleName;

    @Nationalized
    @Column(name = "Phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Nationalized
    @Column(name = "City", nullable = false, length = 30)
    private String city;

    @Column(name = "Rating")
    private Double rating;

    @Column(name = "Driving_expirience", nullable = false)
    private LocalDate drivingExpirience;

    @Column(name = "Registration_date", nullable = false)
    private LocalDate registrationDate;

    @Nationalized
    @Column(name = "Email", length = 50)
    private String email;

    @Column(name = "Birth_date", nullable = false)
    private LocalDate birthDate;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getDrivingExpirience() {
        return drivingExpirience;
    }

    public void setDrivingExpirience(LocalDate drivingExpirience) {
        this.drivingExpirience = drivingExpirience;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

}