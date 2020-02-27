package com.adam.cosmtask.hotel.domain;

import com.adam.cosmtask.hotel.dto.SearchRoomResultDto;
import com.adam.cosmtask.hotel.enums.RoomType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Data
@FieldDefaults(level = PRIVATE)
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"hotel_id", "roomType"})
)
@NoArgsConstructor
class Room {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false, scale = 2)
    BigDecimal dailyPrice;

    @Column(nullable = false)
    Long numberOfRooms;

    @Enumerated(value = STRING)
    RoomType roomType;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Reservation> reservations = new ArrayList<>();

    @ManyToOne
    Hotel hotel;

    public String toString() {
        return "id: " + this.id +
                "dailyPrice: " + this.dailyPrice +
                "numberOfRooms: " + this.numberOfRooms +
                "roomType: " + this.roomType +
                "hotel: " + this.hotel.getId() +
                "reservations size: " + this.reservations.size();
    }

    public Room(BigDecimal dailyPrice, Long numberOfRooms, RoomType roomType) {
        this.dailyPrice = dailyPrice;
        this.numberOfRooms = numberOfRooms;
        this.roomType = roomType;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setRoom(this);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.setRoom(null);
    }

    SearchRoomResultDto toSearchRoomResultDto() {
        SearchRoomResultDto result = new SearchRoomResultDto();
        result.setRoomId(id);
        result.setCity(hotel.getCity());
        result.setHotelName(hotel.getName());
        result.setPrice(dailyPrice);
        result.setRoomType(roomType);
        return result;
    }
}
