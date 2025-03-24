package com.rajkumar.train.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="fare_key")
    private Integer fareKey;
    @Column(name="train_key")
    private Integer trainKey;
    @Column(name="section_key")
    private Integer sectionKey;
    @Column(name="fare")
    private BigDecimal fare;
}
