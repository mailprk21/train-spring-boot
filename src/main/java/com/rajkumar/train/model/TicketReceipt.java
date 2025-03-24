package com.rajkumar.train.model;

import com.rajkumar.train.entity.Ticket;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class TicketReceipt {
    private String from;
    private String to;
    private Integer trainNumber;
    private Date travelDate;
    private Integer ticketNumber;
    private String ticketStatus;
    private Date ticketIssuedDate;
    private BigDecimal pricePaid;
    private User passengerDetail;
    private String section;
    private Integer seatNumber;

}
