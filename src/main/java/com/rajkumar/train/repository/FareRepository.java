package com.rajkumar.train.repository;

import com.rajkumar.train.entity.Fare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FareRepository extends JpaRepository<Fare,Integer> {

    Fare findByTrainKeyAndSectionKey(Integer trainKey, Integer sectionKey);
}
