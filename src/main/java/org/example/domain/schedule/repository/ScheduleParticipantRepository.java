package org.example.domain.schedule.repository;

import java.util.List;
import org.example.domain.schedule.entity.ScheduleParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleParticipantRepository extends JpaRepository<ScheduleParticipant, Long> {
    List<ScheduleParticipant> findByScheduleId(Long scheduleId);

    void deleteByScheduleId(Long scheduleId);
}
