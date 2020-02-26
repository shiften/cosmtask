package com.adam.cosmose.hotel.domain;

import com.adam.cosmose.hotel.enums.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
class CheckReservationsTests {

    private static final String customerEmail = "adam@gmail.com";
    private static final BigDecimal cheaperRoomPrice = new BigDecimal("150");
    private static final BigDecimal expensiveRoomPrice = new BigDecimal("210");

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ReservationRepository repository;

    @BeforeEach
    void setup() {
        Long customerId = saveCustomer();
        saveHotelAndRoom(customerId);
    }

    @Test
    void zeroReservationsWhenWrongEmail() {
        List<Reservation> reservations = repository.findByCustomerEmail("wrong@ema.il");
        assertThat(reservations).isEmpty();
    }

    @Test
    void findsTwoReservationsForUser() {
        List<Reservation> reservations = repository.findByCustomerEmail(customerEmail);
        assertThat(reservations).hasSize(2);
    }

    private Long saveCustomer() {
        Customer customer = new Customer("Adam", customerEmail);
        return (Long) entityManager.persistAndGetId(customer);
    }

    private void saveHotelAndRoom(Long customerId) {
        Hotel hotel = new Hotel("Zamenhoff", "Bialystok");
        Room room1 = new Room(cheaperRoomPrice, 2L, RoomType.DELUXE);
        Room room2 = new Room(expensiveRoomPrice, 2L, RoomType.EXECUTIVE);
        Reservation res1 = new Reservation(LocalDate.parse("2020-02-23"), LocalDate.parse("2020-02-25"));
        Reservation res2 = new Reservation(LocalDate.parse("2020-02-15"), LocalDate.parse("2020-02-29"));
        Customer persistedCus = entityManager.find(Customer.class, customerId);
        persistedCus.addReservation(res1);
        persistedCus.addReservation(res2);
        room1.addReservation(res1);
        room1.addReservation(res2);
        hotel.addRoom(room1);
        hotel.addRoom(room2);
        entityManager.persistAndFlush(hotel);
    }
}
