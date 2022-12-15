package com.jay.getinline.dto;

import com.jay.getinline.constant.EventStatus;

import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        PlaceDto placeDTO,
        String eventName,
        EventStatus eventStatus,
        LocalDateTime eventStartDateTime,
        LocalDateTime eventEndDateTime,
        Integer currentNumberOfPeople,
        Integer capacity,
        String memo
) {
    public static EventResponse of(
            Long id,
            PlaceDto placeDTO,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        return new EventResponse(id, placeDTO, eventName, eventStatus, eventStartDateTime, eventEndDateTime, currentNumberOfPeople, capacity, memo);
    }

    public static EventResponse from(EventDto eventDTO) {
        if (eventDTO == null) return null;
        return EventResponse.of(
                eventDTO.id(),
                eventDTO.placeDto(),
                eventDTO.eventName(),
                eventDTO.eventStatus(),
                eventDTO.eventStartDateTime(),
                eventDTO.eventEndDateTime(),
                eventDTO.currentNumberOfPeople(),
                eventDTO.capacity(),
                eventDTO.memo()
        );
    }
}
