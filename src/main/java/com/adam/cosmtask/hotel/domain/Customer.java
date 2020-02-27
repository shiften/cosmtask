package com.adam.cosmtask.hotel.domain;

import com.adam.cosmtask.hotel.dto.RegisterCustomerDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Data
@FieldDefaults(level = PRIVATE)
@NoArgsConstructor
class Customer {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    String fullName;

    @Column(nullable = false)
    String email;

    @OneToMany(mappedBy = "customer")
    List<Reservation> reservations = new ArrayList<>();

    public Customer(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setCustomer(this);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.setCustomer(null);
    }

    static Customer fromDto(RegisterCustomerDto dto) {
        Customer newCustomer = new Customer();
        newCustomer.setFullName(dto.getFullName());
        newCustomer.setEmail(dto.getEmail());
        return newCustomer;
    }
}
