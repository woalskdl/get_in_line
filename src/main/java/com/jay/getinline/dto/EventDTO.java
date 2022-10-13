package com.jay.getinline.dto;

import com.jay.getinline.constant.EventStatus;
import com.jay.getinline.domain.Event;
import com.jay.getinline.domain.Place;

import java.time.LocalDateTime;

public record EventDTO(
        Long id,
        Long placeId,
        String eventName,
        EventStatus eventStatus,
        LocalDateTime eventStartDateTime,
        LocalDateTime eventEndDateTime,
        Integer currentNumberOfPeople,
        Integer capacity,
        String memo,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static EventDTO of(
            Long id,
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        return new EventDTO(
                id,
                placeId,
                eventName,
                eventStatus,
                eventStartDateTime,
                eventEndDateTime,
                currentNumberOfPeople,
                capacity,
                memo,
                createdAt,
                modifiedAt
        );
    }

    public static EventDTO of(Event event) {
        return new EventDTO(
                event.getId(),
                event.getPlace().getId(),
                event.getEventName(),
                event.getEventStatus(),
                event.getEventStartDatetime(),
                event.getEventEndDatetime(),
                event.getCurrentNumberOfPeople(),
                event.getCapacity(),
                event.getMemo(),
                event.getCreatedAt(),
                event.getModifiedAt()
        );
    }

    public Event toEntity(Place place) {
        return Event.of(
                place,
                eventName,
                eventStatus,
                eventStartDateTime,
                eventEndDateTime,
                currentNumberOfPeople,
                capacity,
                memo
        );
    }

    public Event updateEntity(Event event) {
        if (eventName != null) { event.setEventName(eventName); }
        if (eventStatus != null) { event.setEventStatus(eventStatus); }
        if (eventStartDateTime != null) { event.setEventStartDatetime(eventStartDateTime); }
        if (eventEndDateTime != null) { event.setEventEndDatetime(eventEndDateTime); }
        if (currentNumberOfPeople != null) { event.setCurrentNumberOfPeople(currentNumberOfPeople); }
        if (capacity != null) { event.setCapacity(capacity); }
        if (memo != null) { event.setMemo(memo); }

        return event;
    }
}
