package com.adam.cosmtask.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SearchRoomDto {
    String city;
    BigDecimal priceFrom;
    BigDecimal priceTo;
    LocalDate startDay;
    LocalDate endDay;

    public SearchRoomDto(String city,
                         BigDecimal priceFrom,
                         BigDecimal priceTo,
                         String startDay,
                         String endDay) {
        LocalDate sDay = LocalDate.parse(startDay);
        LocalDate eDay = LocalDate.parse(endDay);
        if (sDay.compareTo(eDay) >= 0 || sDay.isBefore(LocalDate.now())) {
            throw new RuntimeException("Wrong dates");
        }
        this.city = city;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
        this.startDay = sDay;
        this.endDay = eDay;
    }

}
