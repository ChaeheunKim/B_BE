package org.example.domain.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

public record ScheduleRequestDTO(
        @NotBlank(message = "일정 날짜는 필수입니다.")
        LocalDate period,
        @NotBlank(message = "일정명은 필수입니다.")
        String name,
        @NotBlank(message = "참석자 리스트는 필수입니다.")
        List<String> participant,
        @NotBlank(message = "내용은 필수입니다.")
        String content
) {
}
