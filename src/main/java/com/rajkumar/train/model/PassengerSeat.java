package com.rajkumar.train.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PassengerSeat {
    private User passenger;
    private Integer seatNumber;

}
