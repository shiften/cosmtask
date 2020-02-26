package com.adam.cosmose.hotel.dto;

import com.adam.cosmose.hotel.enums.RoomType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CheckReservationsResultDto {

    Long reservationId;
    String hotelName;
    RoomType roomType;
    BigDecimal dailyPrice;
    LocalDate startDay;
    LocalDate endDay;
}
