package com.rajkumar.train.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ticket")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ticket_key")
    private Integer ticketKey;
    @Column(name="train_key")
    private Integer trainKey;
    @Column(name = "section_key")
    private Integer sectionKey;
    @Column(name="status")
    private String status;
    @Column(name="paid_amount")
    private BigDecimal paidAmount;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="email")
    private String email;
    @Column(name="issued_date")
    private Date issuedDate;
}
