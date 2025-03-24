package com.rajkumar.train.controller;

import com.rajkumar.train.model.SectionBooking;
import com.rajkumar.train.service.TrainTicketAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class TrainTicketAdminController {
    private final TrainTicketAdminService service;

    @GetMapping("/api/train/section-booking")
    public SectionBooking getSectionBooking(@RequestParam Integer trainNumber,
                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date travelDate,
                                            @RequestParam String section){
        return service.getSectionBookings(trainNumber,travelDate,section);
    }

}
