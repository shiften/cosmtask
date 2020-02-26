package com.adam.cosmose.hotel.dto;

import com.adam.cosmose.hotel.domain.Room;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CheckReservationsResultDto {

    Long reservationId;
    String hotelName;
    Room.RoomType roomType;
    BigDecimal dailyPrice;
    LocalDate startDay;
    LocalDate endDay;
}
