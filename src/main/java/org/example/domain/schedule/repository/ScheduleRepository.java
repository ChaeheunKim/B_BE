package org.example.domain.schedule.repository;

import java.util.List;
import org.example.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("SELECT s FROM Schedule s WHERE YEAR(s.period) = :year AND MONTH(s.period) = :month")
    List<Schedule> findSchedulesByYearAndMonth(@Param("year") int year, @Param("month") int month);
}
