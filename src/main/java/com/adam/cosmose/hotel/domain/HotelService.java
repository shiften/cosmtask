package com.adam.cosmose.hotel.domain;

import com.adam.cosmose.hotel.dto.*;
import com.adam.cosmose.hotel.enums.RoomType;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.adam.cosmose.hotel.domain.Customer.fromDto;
import static lombok.AccessLevel.PRIVATE;

@Service
@FieldDefaults(level = PRIVATE)
public class HotelService {

    CustomerRepository customerRepository;
    RoomRepository roomRepository;
    ReservationRepository reservationRepository;
    HotelRepository hotelRepository;

    @Value("${insertTestData}")
    boolean shouldInsertTestData;

    @PostConstruct
    void init() {
        if (this.shouldInsertTestData) {
            insertTestData();
        }
    }

    public HotelService(CustomerRepository customerRepository, RoomRepository roomRepository,
                        ReservationRepository reservationRepository, HotelRepository hotelRepository) {
        this.customerRepository = customerRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
    }

    @Transactional
    public void saveCustomer(RegisterCustomerDto registerCustomerDto) {
        if (customerRepository.existsByEmail(registerCustomerDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        customerRepository.save(fromDto(registerCustomerDto));
    }

    @Transactional
    public List<SearchRoomResultDto> searchRooms(SearchRoomDto searchRoomDto) {
        List<Room> notBookedRooms;
        notBookedRooms = roomRepository.findAvailableRooms(searchRoomDto.getCity(),
                searchRoomDto.getPriceFrom(),
                searchRoomDto.getPriceTo(),
                searchRoomDto.getStartDay(),
                searchRoomDto.getEndDay()
        );
        return notBookedRooms.stream().map(Room::toSearchRoomResultDto).collect(Collectors.toList());
    }

    @Transactional
    public void bookRoom(BookRoomDto bookRoomDto) {
        if (bookRoomDto.getStartDay().compareTo(bookRoomDto.getEndDay()) >= 0 || bookRoomDto.getStartDay().isBefore(LocalDate.now())) {
            throw new RuntimeException("Wrong dates");
        }
        Customer customer = customerRepository.findByEmail(bookRoomDto.getUserEmail()).orElseThrow(() -> new RuntimeException("No user with this email"));
        Room availableRoom = roomRepository.findAvailableRoomWithLock(bookRoomDto.getRoomId(), bookRoomDto.getStartDay(), bookRoomDto.getEndDay())
                .orElseThrow(() -> new RuntimeException("No available room"));
        Reservation newReservation = new Reservation(bookRoomDto.getStartDay(), bookRoomDto.getEndDay());
        availableRoom.addReservation(newReservation);
        customer.addReservation(newReservation);
    }

    @Transactional
    public List<CheckReservationsResultDto> checkReservations(String customerEmail) {
        List<CheckReservationsResultDto> result = new ArrayList<>();
        List<Reservation> userReservations = reservationRepository.findByCustomerEmail(customerEmail);
        userReservations.forEach(reservation -> {
            CheckReservationsResultDto dto = new CheckReservationsResultDto();
            dto.setReservationId(reservation.getId());
            dto.setHotelName(reservation.getRoom().getHotel().getName());
            dto.setRoomType(reservation.getRoom().getRoomType());
            dto.setDailyPrice(reservation.getRoom().getDailyPrice());
            dto.setStartDay(reservation.getStartDay());
            dto.setEndDay(reservation.getEndDay());
            result.add(dto);
        });
        return result;
    }

    @Transactional
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new RuntimeException("No reservation with this ID"));
        if (reservation.getStartDay().compareTo(LocalDate.now()) <= 0) {
            throw new RuntimeException("You can cancel reservation only at least one day before it starts");
        }
        Room room = reservation.getRoom();
        room.removeReservation(reservation);
    }

    private void insertTestData() {
        saveHotelOne();
        saveHotelTwo();
    }

    private void saveHotelOne() {
        Customer cus1 = new Customer("Adam", "ss@ss.pl");
        Hotel hotel = new Hotel("Golab", "Bialystok");
        Room room1 = new Room(new BigDecimal("200"), 50L, RoomType.CLASSIC);
        hotel.addRoom(room1);
        Reservation res1 = new Reservation(LocalDate.now(), LocalDate.now().plusDays(2));
        customerRepository.save(cus1);
        cus1.addReservation(res1);
        room1.addReservation(res1);
        hotelRepository.save(hotel);
    }

    private void saveHotelTwo() {
        Customer cus1 = new Customer("Marian", "aa@aa.pl");
        Hotel hotel = new Hotel("Zamenhoff", "Bialystok");
        Room room1 = new Room(new BigDecimal("150"), 2L, RoomType.DELUXE);
        Room room2 = new Room(new BigDecimal("210"), 2L, RoomType.EXECUTIVE);
        hotel.addRoom(room1);
        hotel.addRoom(room2);
        Reservation res1 = new Reservation(LocalDate.parse("2020-03-01"), LocalDate.parse("2020-03-03"));
        Reservation res2 = new Reservation(LocalDate.parse("2020-02-25"), LocalDate.parse("2020-03-10"));
        customerRepository.save(cus1);
        cus1.addReservation(res1);
        cus1.addReservation(res2);
        room1.addReservation(res1);
        room1.addReservation(res2);
        hotelRepository.save(hotel);
    }
}
