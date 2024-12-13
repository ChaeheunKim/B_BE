package org.example.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ScheduleListDTO {
    private final Long scheduleId;
    private final String title;

    @Builder
    public ScheduleListDTO(Long scheduleId, String title) {
        this.scheduleId = scheduleId;
        this.title = title;
    }
}
