package org.example.lb3.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table (name = "Passenger")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Passenger_id", nullable = false)
    @JsonProperty
    private Integer id;

    @Nationalized
    @Column(name = "First_name", length = 40)
    @JsonProperty()
    private String firstName;

    @Nationalized
    @Column(name = "Last_name", length = 50)
    @JsonProperty
    private String lastName;

    @Nationalized
    @Column(name = "Phone_number", length = 15)
    @JsonProperty
    private String phoneNumber;

    @Column(name = "Birth_date")
    @JsonProperty
    private LocalDate birthDate;

    @Nationalized
    @Column(name = "Gender", length = 8)
    @JsonProperty
    private String gender;

    @Nationalized
    @Column(name = "Email", nullable = false, length = 50)
    @JsonProperty
    private String email;

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("passenger")
    private List<Order> orders;


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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}