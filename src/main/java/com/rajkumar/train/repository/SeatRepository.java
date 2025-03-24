package com.rajkumar.train.repository;

import com.rajkumar.train.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Integer> {

    @Query(value = "SELECT MAX(SEAT_NUMBER) FROM SEAT " +
            "WHERE TRAIN_KEY =:trainKey " +
            "AND SECTION_KEY =:sectionKey",nativeQuery = true )
    int getMaxSeatNumber(Integer trainKey, Integer sectionKey);

    /*
    * This logic handles book(seat1)-> book(seat2) -> cancel(seat1) -> book(seat1)
    * cancelled seats are brought back for booking
    *
    * */
    @Query(value = "select X from system_range(\n" +
            "(select start_seat from section where section_key=:sectionKey),\n" +
            "(select end_seat from section where section_key=:sectionKey)) where X not in\n" +
            "(select seat_number from seat where train_key=:trainKey and section_key=:sectionKey)\n" +
            "limit 1", nativeQuery = true)
    int getNextAvailableSeat(Integer trainKey, Integer sectionKey);

    Seat findByTicketKey(Integer ticketKey);

    @Query(value = "SELECT COUNT(SEAT_NUMBER) FROM SEAT " +
            "WHERE TRAIN_KEY =:trainKey " +
            "AND SEAT_NUMBER =:seatNumber",nativeQuery = true )
    int findSeatByCriteria(Integer trainKey, Integer seatNumber);

    @Query (value = "SELECT SEAT_KEY, TRAIN_KEY,SECTION_KEY, TICKET_KEY, SEAT_NUMBER " +
            "FROM SEAT " +
            "WHERE TRAIN_KEY=:trainKey " +
            " AND SECTION_KEY = :sectionKey", nativeQuery = true)
    List<Seat> findAllSeats(Integer trainKey, Integer sectionKey);
}
