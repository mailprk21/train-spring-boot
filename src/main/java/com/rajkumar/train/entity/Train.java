package com.rajkumar.train.entity;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="train")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "train_key")
    private Integer trainKey;
    @Column(name = "train_no")
    private Integer trainNo;
    @Column(name = "train_date" )
    private Date trainDate;
    @Column(name = "origin")
    private String origin;
    @Column(name = "destination")
    private String destination;

}
