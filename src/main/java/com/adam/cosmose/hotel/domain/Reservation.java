package com.adam.cosmose.hotel.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Data
@FieldDefaults(level = PRIVATE)
@NoArgsConstructor
class Reservation {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    LocalDate startDay;

    @Column(nullable = false)
    LocalDate endDay;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Room room;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Customer customer;

    public String toString() {
        return "id: " + this.id +
                "startDay: " + this.startDay +
                "endDay: " + this.endDay +
                "room: " + this.room.getId() +
                "customer: " + this.customer.getId();
    }

    public Reservation(LocalDate startDay, LocalDate endDay) {
        this.startDay = startDay;
        this.endDay = endDay;
    }
}
