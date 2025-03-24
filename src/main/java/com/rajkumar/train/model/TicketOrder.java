package com.rajkumar.train.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class TicketOrder {
    private User user;
    private String origin;
    private String destination;
    private String section;
    private String travelDate;
    private BigDecimal fareAmount;
}
