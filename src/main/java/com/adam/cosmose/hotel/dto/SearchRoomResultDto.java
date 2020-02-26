package com.adam.cosmose.hotel.dto;

import com.adam.cosmose.hotel.domain.Room;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SearchRoomResultDto {
    Long roomId;
    String hotelName;
    String city;
    BigDecimal price;
    Room.RoomType roomType;
}
