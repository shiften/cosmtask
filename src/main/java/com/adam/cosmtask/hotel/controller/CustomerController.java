package com.adam.cosmtask.hotel.controller;

import com.adam.cosmtask.hotel.domain.HotelService;
import com.adam.cosmtask.hotel.dto.RegisterCustomerDto;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/customer")
class CustomerController {

    HotelService hotelService;

    public CustomerController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody @Valid RegisterCustomerDto dto) {
        hotelService.saveCustomer(dto);
    }

}
