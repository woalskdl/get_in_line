package com.jay.getinline.repository.jooq;

import com.jay.getinline.constant.EventStatus;
import com.jay.getinline.dto.EventViewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface EventRepositoryJooq {

    Page<EventViewResponse> findEventViewPageBySearchParams (
            String placeName,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime,
            Pageable pageable
    );

}
