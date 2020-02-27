package com.adam.cosmtask.hotel.dto;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class RegisterCustomerDto {
    @NotNull
    @NotBlank
    String fullName;

    @NotNull
    @Email
    String email;


}
