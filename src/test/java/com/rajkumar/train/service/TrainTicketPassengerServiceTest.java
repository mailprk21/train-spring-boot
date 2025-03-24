package com.rajkumar.train.service;

import com.rajkumar.train.entity.Section;
import com.rajkumar.train.entity.Train;
import com.rajkumar.train.exception.BadRequestException;
import com.rajkumar.train.model.TicketOrder;
import com.rajkumar.train.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TrainTicketPassengerService.class})
class TrainTicketPassengerServiceTest {

    @Autowired
    TrainTicketPassengerService service;
    @MockitoBean
    TrainRepository trainRepository;
    @MockitoBean
    SectionRepository sectionRepository;
    @MockitoBean
    FareRepository fareRepository;
    @MockitoBean
    SeatRepository seatRepository;
    @MockitoBean
    TicketRepository ticketRepository;

    @Test
    void test_bookTicket_train_not_found() throws Exception{
        //GIVEN
        when(trainRepository.findTrain(any(),any(),any())).thenReturn(Optional.empty());
        //WHEN
        BadRequestException exception = assertThrows(BadRequestException.class, () ->
                service.bookTicket(TicketOrder.builder().build()));
        //THEN
        assertEquals("Train Not found",exception.getMessage());

    }

    @Test
    void test_bookTicket_train_fully_booked() throws Exception{
        //GIVEN
        Train train = Train.builder().trainKey(1).trainDate(new Date()).trainNo(100).build();
        when(trainRepository.findTrain(any(),any(),any())).thenReturn(Optional.of(train));
        when(sectionRepository.findBySectionName(any())).thenReturn(Section.builder().sectionName("A").startSeat(1).endSeat(5).build());
        when(seatRepository.count(any())).thenReturn(5l);
        //WHEN
        BadRequestException exception = assertThrows(BadRequestException.class, () ->
                service.bookTicket(TicketOrder.builder().build()));
        //THEN
        assertEquals("Selected train is fully booked",exception.getMessage());

    }
}