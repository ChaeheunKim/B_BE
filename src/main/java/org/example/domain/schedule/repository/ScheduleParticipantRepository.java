package org.example.domain.schedule.repository;

import org.example.domain.schedule.entity.ScheduleParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleParticipantRepository extends JpaRepository<ScheduleParticipant, Long> {

}
