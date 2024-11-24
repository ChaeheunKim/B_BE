package org.example.domain.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.schedule.dto.request.ScheduleRequestDTO;
import org.example.domain.schedule.service.ScheduleService;
import org.example.global.errors.ErrorCode;
import org.example.global.response.ResponseEntityProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ResponseEntityProvider<?> responseEntityProvider;

    @PostMapping(value = "/schedule")
    public ResponseEntity<?> createSchedule(@RequestBody ScheduleRequestDTO requestDTO){
        try {
            scheduleService.createSchedule(requestDTO);
            return responseEntityProvider.successWithoutData("일정 추가 완료");
        }catch (IllegalArgumentException e) {
            return  responseEntityProvider.FailWithoutData(ErrorCode.INVALID_ARGUMENT);
        }
    }

//    @GetMapping(value = "/schedule")
//    public ResponseEntity<?> searchSchedules(@RequestParam int year, @RequestParam int month) {
//        List<ScheduleListDTO> scheduleList = scheduleService.searchSchedules(year, month);
//        return responseEntityProvider.successWithData("리스트를 성공적으로 불러왔습니다.", scheduleList);
//    }
}
