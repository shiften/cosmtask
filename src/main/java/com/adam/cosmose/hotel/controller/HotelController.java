package com.adam.cosmose.hotel.controller;


import com.adam.cosmose.hotel.domain.HotelService;
import com.adam.cosmose.hotel.dto.BookRoomDto;
import com.adam.cosmose.hotel.dto.CheckReservationsResultDto;
import com.adam.cosmose.hotel.dto.SearchRoomDto;
import com.adam.cosmose.hotel.dto.SearchRoomResultDto;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    HotelService hotelService;

    HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/rooms/searchroom")
    public List<SearchRoomResultDto> searchRooms(@RequestParam(required = false) String city,
                                                 @RequestParam(required = false) BigDecimal priceFrom,
                                                 @RequestParam(required = false) BigDecimal priceTo,
                                                 @RequestParam String startDay,
                                                 @RequestParam String endDay) {
        return hotelService.searchRooms(new SearchRoomDto(city, priceFrom, priceTo, startDay, endDay));
    }

    @PostMapping("/rooms/bookroom")
    public void bookRoom(@RequestBody BookRoomDto bookRoomDto) {
        hotelService.bookRoom(bookRoomDto);
    }

    @GetMapping("/reservations/check")
    public List<CheckReservationsResultDto> checkReservations(@RequestParam(required = false) String customerEmail) {
        return hotelService.checkReservations(customerEmail);
    }

    @DeleteMapping("/reservations/cancel/{reservationId}")
    public void cancelReservation(@PathVariable("reservationId") Long reservationId) {
        hotelService.cancelReservation(reservationId);
    }
}