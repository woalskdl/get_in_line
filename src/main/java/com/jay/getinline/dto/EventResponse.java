package com.jay.getinline.dto;

import com.jay.getinline.constant.EventStatus;

import java.time.LocalDateTime;

public record EventResponse(
        Long placeId,
        String eventName,
        EventStatus eventStatus,
        LocalDateTime eventStartDateTime,
        LocalDateTime eventEndDateTime,
        int currentNumberOfPeople,
        int capacity,
        String memo
) {
    public static EventResponse of(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime,
            int currentNumberOfPeople,
            int capacity,
            String memo
    ) {
        return new EventResponse(placeId, eventName, eventStatus, eventStartDateTime, eventEndDateTime, currentNumberOfPeople, capacity, memo);
    }
}
