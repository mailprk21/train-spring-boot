package com.rajkumar.train.controller;

import com.rajkumar.train.model.*;
import com.rajkumar.train.service.TrainTicketPassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class TrainTicketPassengerController {

    private final TrainTicketPassengerService service;

    @PostMapping("/api/train/ticket")
    public ResponseEntity<TicketReceipt> bookTicket(@RequestBody TicketOrder order){

        TicketReceipt receipt = service.bookTicket(order);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(receipt.getTicketNumber())
                .toUri();

        return ResponseEntity.created(location).body(receipt);
    }

    @GetMapping("/api/train/ticket/{ticketNo}")
    public TicketReceipt getTicket(@PathVariable Integer ticketNo){

        return service.getTicket(ticketNo);
    }

    @DeleteMapping("/api/train/ticket/{ticketNo}")
    public TicketReceipt cancelTicket(@PathVariable Integer ticketNo){
        return service.cancelTicket(ticketNo);
    }

    @PutMapping("/api/train/ticket/{ticketNo}")
    public TicketReceipt changeSeat(@RequestBody SeatChangeRequest request,@PathVariable Integer ticketNo){
        return service.changeSeat(ticketNo,request);
    }

}
