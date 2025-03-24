package com.rajkumar.train.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "section")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_key")
    private Integer sectionKey;
    @Column(name="section_name")
    private String sectionName;
    @Column(name="start_seat")
    private Integer startSeat;
    @Column(name="end_seat")
    private Integer endSeat;
}
