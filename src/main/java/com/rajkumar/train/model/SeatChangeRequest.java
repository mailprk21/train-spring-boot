package com.rajkumar.train.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatChangeRequest {
    private Integer ticketNumber;
    private Integer seatNuumber;
}
