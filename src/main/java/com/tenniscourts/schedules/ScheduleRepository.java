package com.tenniscourts.schedules;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByTennisCourt_IdOrderByStartDateTime(Long id); 
    @Query(value = "SELECT u FROM Schedule u where u.startDateTime = strtdDate and u.endDateTime = endDate ")
    List<Schedule> findByStartAndEndDate(LocalDateTime strtdDate ,LocalDateTime endDate);
    
}