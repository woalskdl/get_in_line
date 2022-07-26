package com.jay.getinline.domain;

import com.jay.getinline.constant.EventStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event {
    private Long id;

    private Long placeId;
    private String eventName;
    private EventStatus eventStatus;
    private LocalDateTime eventStartDateTime;
    private LocalDateTime eventEndDateTime;
    private int currentNumberOfPeople;
    private int capacity;
    private String memo;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
