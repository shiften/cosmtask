package com.adam.cosmose.hotel.dto;

import com.adam.cosmose.hotel.enums.RoomType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SearchRoomResultDto {
    Long roomId;
    String hotelName;
    String city;
    BigDecimal price;
    RoomType roomType;
}
