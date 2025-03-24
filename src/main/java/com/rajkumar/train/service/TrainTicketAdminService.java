package com.rajkumar.train.service;

import com.rajkumar.train.entity.Seat;
import com.rajkumar.train.entity.Section;
import com.rajkumar.train.entity.Ticket;
import com.rajkumar.train.entity.Train;
import com.rajkumar.train.exception.BadRequestException;
import com.rajkumar.train.model.PassengerSeat;
import com.rajkumar.train.model.SectionBooking;
import com.rajkumar.train.model.User;
import com.rajkumar.train.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainTicketAdminService {

    private final TrainRepository trainRepository;
    private final SectionRepository sectionRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;

    public SectionBooking getSectionBookings(Integer trainNo, Date travelDate, String sectionName){
        // get train
        Optional<Train> optionalTrain = trainRepository.findByTrainNoAndTrainDate(trainNo, travelDate);
        if(optionalTrain.isEmpty()){
            throw new BadRequestException("Train not found");
        }
        // get section
        Section section= sectionRepository.findBySectionName(sectionName);
        if(section == null){
            throw new BadRequestException("Section not found");
        }
        // retrieve all seats for the section
        List<Seat> seats=seatRepository.findAllSeats(optionalTrain.get().getTrainKey(),section.getSectionKey());
        // retrieve all tickets for the seats and map to response
        List<PassengerSeat> seatList=seats.stream()
                .map(seat -> {
                    Ticket ticket = ticketRepository.findById(seat.getTicketKey()).get();
                    return PassengerSeat.builder()
                            .passenger(User.builder()
                                    .email(ticket.getEmail())
                                    .firstName(ticket.getFirstName())
                                    .lastName(ticket.getLastName())
                                    .build())
                            .seatNumber(seat.getSeatNumber())
                            .build();
                }).toList();
        // Build response and return
        return SectionBooking.builder()
                .trainNumber(optionalTrain.get().getTrainNo())
                .travelDate(optionalTrain.get().getTrainDate())
                .section(section.getSectionName())
                .bookedSeats(seatList)
                .build();

    }

}
