package com.rajkumar.train.service;

import com.rajkumar.train.entity.*;
import com.rajkumar.train.exception.BadRequestException;
import com.rajkumar.train.model.*;
import com.rajkumar.train.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainTicketPassengerService {

    private final TrainRepository trainRepository;
    private final SectionRepository sectionRepository;
    private final FareRepository fareRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;

    public TicketReceipt bookTicket(TicketOrder order){
        // Get Train record
        Optional<Train> optionalTrain =  trainRepository.findTrain(order.getTravelDate(), order.getOrigin(), order.getDestination());
        if(optionalTrain.isEmpty()){
            throw new BadRequestException("Train Not found");
        }
        Train train = optionalTrain.get();
        // Get Section record
        Section section = sectionRepository.findBySectionName(order.getSection());
        // Check if seat exists
        Seat seat = Seat.builder().trainKey(train.getTrainKey())
                .sectionKey(section.getSectionKey()).build();
        long bookedSeats = seatRepository.count(Example.of(seat));
        long totalSeats = section.getEndSeat()-section.getStartSeat()+1;
        if(totalSeats == bookedSeats) {
            throw new BadRequestException("Selected train is fully booked");
        }
        // Get Fare and check if payment valid
        Fare fare = fareRepository.findByTrainKeyAndSectionKey(train.getTrainKey(),
                section.getSectionKey());
        if (fare.getFare().compareTo(order.getFareAmount()) !=0) {
            //fares not matching throw error
            throw new BadRequestException("Fares not matching the database fares");
        }
        //  Create Seat record - this blocks the seat
        int currentSeat;
        if (bookedSeats == 0){
            currentSeat = section.getStartSeat();
        } else {
            currentSeat = seatRepository.getNextAvailableSeat(train.getTrainKey(), section.getSectionKey());
        }
        Seat newSeat = Seat.builder().trainKey(train.getTrainKey())
                .sectionKey(section.getSectionKey())
                .seatNumber(currentSeat)
                .build();

        Seat bookedSeat = seatRepository.save(newSeat);
        // Create Ticket
        Ticket ticket =  Ticket.builder().trainKey(train.getTrainKey())
                .sectionKey(section.getSectionKey())
                .email(order.getUser().getEmail())
                .firstName(order.getUser().getFirstName())
                .lastName(order.getUser().getLastName())
                .status("Confirmed")
                .issuedDate(new Date())
                .paidAmount(fare.getFare())
                .build();
        Ticket bookedTicket = ticketRepository.save(ticket);
        //  Update seat with ticket number
        bookedSeat.setTicketKey(bookedTicket.getTicketKey());
        seatRepository.save(bookedSeat);
        //Build Response
        return TicketReceipt.builder()
                .ticketNumber(bookedTicket.getTicketKey())
                .trainNumber(train.getTrainNo())
                .from(train.getOrigin())
                .to(train.getDestination())
                .travelDate(train.getTrainDate())
                .section(section.getSectionName())
                .passengerDetail(order.getUser())
                .ticketStatus(bookedTicket.getStatus())
                .pricePaid(bookedTicket.getPaidAmount())
                .ticketIssuedDate(bookedTicket.getIssuedDate())
                .seatNumber(bookedSeat.getSeatNumber())
                .build();
    }
    public TicketReceipt getTicket(Integer ticketNumber){
        // Fetch Ticket using ticket number
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketNumber);
        if(ticketOptional.isEmpty()){
            throw new BadRequestException("Ticket not found");
        }
        Ticket ticket=ticketOptional.get();
        Train train =  trainRepository.findById(ticket.getTrainKey()).get();
        // Fetch Seat using ticket number
        Seat seat = seatRepository.findByTicketKey(ticket.getTicketKey());
        // Build and return TicketReceipt
        return TicketReceipt.builder()
                .ticketNumber(ticket.getTicketKey())
                .ticketStatus(ticket.getStatus())
                .ticketIssuedDate(ticket.getIssuedDate())
                .trainNumber(train.getTrainNo())
                .from(train.getOrigin())
                .to(train.getDestination())
                .travelDate(train.getTrainDate())
                .section(sectionRepository.findById(ticket.getSectionKey()).get().getSectionName())
                .pricePaid(ticket.getPaidAmount())
                .passengerDetail(User.builder().email(ticket.getEmail())
                                    .firstName(ticket.getFirstName())
                                    .lastName(ticket.getLastName()).build())
                .seatNumber(seat.getSeatNumber())
                .build();
    }
    public TicketReceipt cancelTicket(Integer ticketNumber){
        // Fetch Ticket number
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketNumber);
        if(ticketOptional.isEmpty()){
            throw new BadRequestException("Ticket not found");
        }
        Ticket ticket=ticketOptional.get();
        Train train =  trainRepository.findById(ticket.getTrainKey()).get();
        // Fetch Seat using ticket number
        Seat seat = seatRepository.findByTicketKey(ticket.getTicketKey());
        // Delete Seat
        seatRepository.delete(seat);
        // Update Ticket
        ticket.setStatus("Cancelled");
        ticketRepository.save(ticket);
        // Build and return TicketReceipt

        return TicketReceipt.builder()
                .ticketNumber(ticket.getTicketKey())
                .ticketStatus(ticket.getStatus())
                .ticketIssuedDate(ticket.getIssuedDate())
                .trainNumber(train.getTrainNo())
                .from(train.getOrigin())
                .to(train.getDestination())
                .travelDate(train.getTrainDate())
                .section(sectionRepository.findById(ticket.getSectionKey()).get().getSectionName())
                .pricePaid(ticket.getPaidAmount())
                .passengerDetail(User.builder().email(ticket.getEmail())
                        .firstName(ticket.getFirstName())
                        .lastName(ticket.getLastName()).build())
                .seatNumber(null)
                .build();

    }
    public TicketReceipt changeSeat(Integer ticketNumber, SeatChangeRequest request){
        // Fetch Ticket
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketNumber);
        if(ticketOptional.isEmpty()){
            throw new BadRequestException("Ticket Not found");
        }
        Ticket ticket=ticketOptional.get();
        Train train =  trainRepository.findById(ticket.getTrainKey()).get();
        // Fetch Seat using ticket number
        Seat seat = seatRepository.findByTicketKey(ticket.getTicketKey());
        // Check if seat number exists
        long count=seatRepository.findSeatByCriteria(train.getTrainKey(), request.getSeatNuumber());
        if (count > 0){
            throw new BadRequestException("Selected seat already occupied, cannot change seat");
        }
        // Delete old seat and Insert new seat
        seatRepository.delete(seat);
        Seat newSeat = Seat.builder().ticketKey(ticketNumber).trainKey(train.getTrainKey())
                .sectionKey(ticket.getSectionKey())
                .seatNumber(request.getSeatNuumber())
                .build();
        seatRepository.save(newSeat);
        // Build and return ticket receipt
        return TicketReceipt.builder()
                .ticketNumber(ticket.getTicketKey())
                .ticketStatus(ticket.getStatus())
                .ticketIssuedDate(ticket.getIssuedDate())
                .trainNumber(train.getTrainNo())
                .from(train.getOrigin())
                .to(train.getDestination())
                .travelDate(train.getTrainDate())
                .section(sectionRepository.findById(ticket.getSectionKey()).get().getSectionName())
                .pricePaid(ticket.getPaidAmount())
                .passengerDetail(User.builder().email(ticket.getEmail())
                        .firstName(ticket.getFirstName())
                        .lastName(ticket.getLastName()).build())
                .seatNumber(newSeat.getSeatNumber())
                .build();

    }
}
