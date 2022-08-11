package com.jay.getinline.controller.api;

import com.jay.getinline.constant.EventStatus;
import com.jay.getinline.dto.APIDataResponse;
import com.jay.getinline.dto.EventResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api")
@RestController
public class APIEventController {

    @GetMapping("/events")
    public APIDataResponse<List<EventResponse>> getEvents() {
//        throw new HttpRequestMethodNotSupportedException("스프링 에러 테스트");
        return APIDataResponse.of(List.of(EventResponse.of(
                1L,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2022, 8, 3, 9, 0, 1),
                LocalDateTime.of(2022, 8, 3, 18, 0, 1),
                30,
                50,
                "마스크를 꼭 착용하세요."
        )));
    }

    @PostMapping("/events")
    public APIDataResponse<Boolean> createEvent() {
//        throw new GeneralException("장군님");
        return APIDataResponse.of(true);
    }

    @GetMapping("/events/{eventId}")
    public String getEvent(@PathVariable Integer eventId) {
        throw new RuntimeException("런타임 에러");
//        return "event : " + eventId;
    }

    @PutMapping("/events/{eventId}")
    public Boolean modifyEvent(@PathVariable Integer eventId) {
        return true;
    }

    @DeleteMapping("/events/{eventId}")
    public Boolean removeEvent(@PathVariable Integer eventId) {
        return true;
    }
}
