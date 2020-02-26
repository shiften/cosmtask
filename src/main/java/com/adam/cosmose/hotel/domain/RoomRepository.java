package com.adam.cosmose.hotel.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByIdNotIn(Collection<Long> ids);

    @Query("select r from Room r left join r.hotel h where h.city = :city" +
            " and (:dailyPriceFrom is null or r.dailyPrice >= :dailyPriceFrom) " +
            " and (:dailyPriceTo is null or r.dailyPrice <= :dailyPriceTo)" +
            " and r.numberOfRooms > (select count(res) from Reservation res where" +
            " res.startDay < :endDay and res.endDay > :startDay" +
            " and res.room = r)")
    List<Room> findAvailableRooms(String city, @Param("dailyPriceFrom") BigDecimal dailyPriceFrom, @Param("dailyPriceTo") BigDecimal dailyPriceTo, LocalDate startDay, LocalDate endDay);

    @Query("select r from Room r where r.id = :id and r.numberOfRooms > (select count(res) from Reservation res where" +
            " res.startDay < :endDay and res.endDay > :startDay and res.room = r)")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Room> findAvailableRoomWithLock(Long id, LocalDate startDay, LocalDate endDay);

}
