package com.rajkumar.train.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class SectionBooking {
    private Integer trainNumber;
    private Date travelDate;
    private String section;
    private List<PassengerSeat> bookedSeats;
}
