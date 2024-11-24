package org.example.domain.schedule.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.domain.schedule.dto.request.ScheduleRequestDTO;
import org.example.domain.schedule.dto.response.ScheduleListDTO;
import org.example.domain.schedule.entity.Schedule;
import org.example.domain.schedule.entity.ScheduleParticipant;
import org.example.domain.schedule.repository.ScheduleParticipantRepository;
import org.example.domain.schedule.repository.ScheduleRepository;
import org.example.domain.user.UserEntity.User;
import org.example.domain.user.UserRepository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final ScheduleParticipantRepository scheduleParticipantRepository;

    // 일정 생성
    public void createSchedule(ScheduleRequestDTO dto) {
        Schedule savedSchedule = scheduleRepository.save(
                Schedule.builder()
                        .name(dto.name())
                        .period(dto.period())
                        .content(dto.content())
                        .build()
        );

        // 참가자 Map에서 이름 추출
        List<String> names = dto.participant();

        // user들 DB 조회
        List<User> users = userRepository.findByNameIn(names);

        users.forEach(user -> {
            ScheduleParticipant participant = ScheduleParticipant.builder()
                    .schedule(savedSchedule)
                    .user(user)
                    .userName(user.getName())
                    .build();
            scheduleParticipantRepository.save(participant);
        });
    }

    public List<ScheduleListDTO> searchSchedules(int year, int month) {
        List<Schedule> schedules = scheduleRepository.findSchedulesByYearAndMonth(year, month);

        return schedules.stream()
                .map(schedule -> ScheduleListDTO.builder()
                        .scheduleId(schedule.getId())
                        .title(schedule.getName())
                        .build())
                .toList();
    }

    public List<String> getParticipantList(Long scheduleId) {
        List<ScheduleParticipant> participants = scheduleParticipantRepository.findByScheduleId(scheduleId);

        // 이름만 추출하여 리스트 반환
        return participants.stream()
                .map(ScheduleParticipant::getUserName)
                .collect(Collectors.toList());
    }

    public void deleteSchedule(Long scheduleId) {
        scheduleParticipantRepository.deleteByScheduleId(scheduleId);
        scheduleRepository.deleteById(scheduleId);
    }
}
