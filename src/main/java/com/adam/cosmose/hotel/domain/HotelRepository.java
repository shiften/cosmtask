package com.adam.cosmose.hotel.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface HotelRepository extends JpaRepository<Hotel, Long> {
}
