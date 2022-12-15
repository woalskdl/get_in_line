package com.jay.getinline.controller.api;

import com.jay.getinline.constant.EventStatus;
import com.jay.getinline.constant.PlaceType;
import com.jay.getinline.dto.APIDataResponse;
import com.jay.getinline.dto.EventRequest;
import com.jay.getinline.dto.EventResponse;
import com.jay.getinline.dto.PlaceDto;
import com.jay.getinline.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

// data-rest 를 이용할 경우 현재 API 를 타지 않는다. 세팅된 기본 동작으로 작동함.

/**
 * Spring Data REST 로 API 를 만들어서 당장 필요가 없어진 컨트롤러.
 * 우선 deprecated 하고, 향후 사용 방안을 고민해 본다.
 * 필요에 따라서는 다시 살릴 수도 있음
 *
 * @deprecated 0.1.2
 */
@Deprecated
@RequiredArgsConstructor
//@Validated
//@RequestMapping("/api")
//@RestController
@Slf4j
public class APIEventController {

    private final EventService eventService;

    // @RequestParam 어노테이션 없으면 required=false 로 자동 적용
    @GetMapping("/events")
    public APIDataResponse<List<EventResponse>> getEvents(
            @Positive Long placeId,
            @Size(min = 2) String eventName,
            EventStatus eventStatus,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventStartDateTime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventEndDateTime
    ) {
//        List<EventResponse> response = eventService
//                .getEvents(placeId, eventName, eventStatus, eventStartDateTime, eventEndDateTime)
//                .stream().map(EventResponse::from).toList();
//
//        return APIDataResponse.of(response);
        return APIDataResponse.of(List.of(EventResponse.of(
                1L,
                PlaceDto.of(
                        1L,
                        PlaceType.SPORTS,
                        "배드민턴장",
                        "서울시 가나구 다라동",
                        "010-1111-2222",
                        0,
                        null,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ),
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                0,
                24,
                "마스크 꼭 착용하세요"
        )));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/events")
    public APIDataResponse<String> createEvent(@RequestBody @Valid EventRequest eventRequest) {
        log.debug("보고 싶은 값 : {}", eventRequest);
        boolean result = eventService.createEvent(eventRequest.toDTO());
        return APIDataResponse.of(Boolean.toString(result));
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
