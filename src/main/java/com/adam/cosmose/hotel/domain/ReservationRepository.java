package com.adam.cosmose.hotel.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCustomerEmail(String customerEmail);
}
