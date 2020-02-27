package com.adam.cosmtask.hotel.domain;

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
class Hotel {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String city;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    List<Room> rooms = new ArrayList<>();

    public Hotel(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public void addRoom(Room room) {
        rooms.add(room);
        room.setHotel(this);
    }

    @Override
    public String toString() {
        return "id: " + this.id +
                "name: " + this.name +
                "city: " + this.city +
                "Number of rooms: " + rooms.size();
    }
}
