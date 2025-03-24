package com.rajkumar.train.repository;

import com.rajkumar.train.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

public interface TrainRepository extends JpaRepository<Train, Integer> {

    Optional<Train> findByTrainNoAndTrainDate(Integer trainNo, Date trainDate);

    @Query(value = " SELECT TRAIN_KEY, TRAIN_NO, TRAIN_DATE, ORIGIN, DESTINATION " +
            "FROM TRAIN " +
            "WHERE TRAIN_DATE = :trainDate " +
            "AND ORIGIN = :origin " +
            "AND DESTINATION = :destination", nativeQuery = true)
    Optional<Train> findTrain(String trainDate, String origin, String destination);
}
