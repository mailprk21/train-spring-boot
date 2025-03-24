package com.rajkumar.train.service;

import com.rajkumar.train.entity.Seat;
import com.rajkumar.train.entity.Section;
import com.rajkumar.train.entity.Ticket;
import com.rajkumar.train.entity.Train;
import com.rajkumar.train.exception.BadRequestException;
import com.rajkumar.train.model.SectionBooking;
import com.rajkumar.train.repository.SeatRepository;
import com.rajkumar.train.repository.SectionRepository;
import com.rajkumar.train.repository.TicketRepository;
import com.rajkumar.train.repository.TrainRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TrainTicketAdminService.class})
class TrainTicketAdminServiceTest {

    @Autowired
    TrainTicketAdminService service;

    @MockitoBean
    TrainRepository trainRepository;
    @MockitoBean
    SectionRepository sectionRepository;
    @MockitoBean
    SeatRepository seatRepository;
    @MockitoBean
    TicketRepository ticketRepository;

    @Test
    void test_getSectionBookings_train_not_found() throws Exception{
        //GIVEN
        when(trainRepository.findByTrainNoAndTrainDate(any(),any())).thenReturn(Optional.empty());
        //WHEN
        BadRequestException exception = assertThrows(BadRequestException.class, () ->
        service.getSectionBookings(1,new Date(),"A"));
        //THEN
        assertEquals("Train not found",exception.getMessage());
    }

    @Test
    void test_getSectionBookings_section_not_found() throws Exception {
        //GIVEN
        when(trainRepository.findByTrainNoAndTrainDate(any(), any())).thenReturn(Optional.of(Train.builder().build()));
        when(sectionRepository.findBySectionName(any())).thenReturn(null);
        //WHEN
        BadRequestException exception = assertThrows(BadRequestException.class, () ->
                service.getSectionBookings(1, new Date(), "A"));
        //THEN
        assertEquals("Section not found", exception.getMessage());
    }
    @Test
    void test_getSectionBookings_no_seats() throws Exception {
        //GIVEN
        when(trainRepository.findByTrainNoAndTrainDate(any(), any())).thenReturn(Optional.of(Train.builder()
                        .trainNo(100)
                        .trainDate(new Date()).build()));
        when(sectionRepository.findBySectionName(any())).thenReturn(Section.builder().sectionName("A").build());
        when(seatRepository.findAllSeats(any(),any())).thenReturn(List.of());
        //WHEN
        SectionBooking booking=service.getSectionBookings(100,new Date(),"A");

        //THEN
        assertEquals(100,booking.getTrainNumber());
        assertEquals("A",booking.getSection());
        assertEquals(0,booking.getBookedSeats().size());
    }
    @Test
    void test_getSectionBookings_with_seats() throws Exception {
        //GIVEN
        //PassengerSeat seat1=PassengerSeat.builder().seatNumber(1).passenger(User.builder().firstName("A").lastName("B").email("abc@abc.com").build()).build();
        //PassengerSeat seat2=PassengerSeat.builder().seatNumber(1).passenger(User.builder().firstName("A").lastName("B").email("abc@abc.com").build()).build();
        Seat seat1 = Seat.builder().seatNumber(1).seatKey(1).trainKey(1).ticketKey(1).build();
        Seat seat2 = Seat.builder().seatNumber(1).seatKey(2).trainKey(1).ticketKey(2).build();
        Ticket ticket1= Ticket.builder().ticketKey(1).firstName("A").lastName("B").email("abc@abc.com").build();
        Ticket ticket2= Ticket.builder().ticketKey(2).firstName("X").lastName("Y").email("xyz@xyz.com").build();
        when(trainRepository.findByTrainNoAndTrainDate(any(), any())).thenReturn(Optional.of(Train.builder()
                .trainNo(100)
                .trainDate(new Date()).build()));
        when(sectionRepository.findBySectionName(any())).thenReturn(Section.builder().sectionName("A").build());
        when(seatRepository.findAllSeats(any(),any())).thenReturn(List.of(seat1,seat2));
        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket1));
        when(ticketRepository.findById(2)).thenReturn(Optional.of(ticket2));
        //WHEN
        SectionBooking booking=service.getSectionBookings(100,new Date(),"A");

        //THEN
        assertEquals(100,booking.getTrainNumber());
        assertEquals("A",booking.getSection());
        assertEquals(2,booking.getBookedSeats().size());

    }
    }