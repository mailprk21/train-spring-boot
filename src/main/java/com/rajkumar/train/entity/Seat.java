package com.rajkumar.train.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seat")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_key")
    private Integer seatKey;
    @Column(name = "train_key")
    private Integer trainKey;
    @Column(name = "section_key")
    private Integer sectionKey;
    @Column(name = "ticket_key")
    private Integer ticketKey;
    @Column(name = "seat_number")
    private Integer seatNumber;
}
