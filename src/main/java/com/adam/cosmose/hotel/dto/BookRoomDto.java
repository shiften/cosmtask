package com.adam.cosmose.hotel.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class BookRoomDto {
    Long roomId;
    String userEmail;
    LocalDate startDay;
    LocalDate endDay;
}
