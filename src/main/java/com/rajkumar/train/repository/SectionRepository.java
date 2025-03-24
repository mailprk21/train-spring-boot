package com.rajkumar.train.repository;

import com.rajkumar.train.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Integer> {

    Section findBySectionName(String sectionName);
}
