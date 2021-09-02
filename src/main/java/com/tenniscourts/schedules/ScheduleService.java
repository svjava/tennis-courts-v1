package com.tenniscourts.schedules;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tenniscourts.exceptions.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    public ScheduleDTO addSchedule(Long tennisCourtId, CreateScheduleRequestDTO createScheduleRequestDTO) {
    	
    	List<Schedule> isScheduleAvailable = scheduleRepository.findByStartAndEndDate(createScheduleRequestDTO.getStartDateTime(), createScheduleRequestDTO.getStartDateTime().plusHours(1));
    	if(CollectionUtils.isEmpty((isScheduleAvailable))) {
    		final Schedule newSchedule = scheduleMapper.map(createScheduleRequestDTO);
        	newSchedule.setEndDateTime(createScheduleRequestDTO.getStartDateTime().plusHours(1));
            return scheduleMapper.map(scheduleRepository.save(newSchedule));
    	} else {
    		 throw new EntityNotFoundException("Requested schedule time is not available.");
    	}
    
    }

    public List<ScheduleDTO> findSchedulesByDates(LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleMapper.map(scheduleRepository.findByStartAndEndDate(startDate, endDate));
    }

    public ScheduleDTO findSchedule(Long scheduleId) {
        return scheduleMapper.map(scheduleRepository.findById(scheduleId).get());
    }

    public List<ScheduleDTO> findSchedulesByTennisCourtId(Long tennisCourtId) {
        return scheduleMapper.map(scheduleRepository.findByTennisCourt_IdOrderByStartDateTime(tennisCourtId));
    }
}
