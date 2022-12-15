package com.jay.getinline.repository.querydsl;

import com.jay.getinline.constant.ErrorCode;
import com.jay.getinline.constant.EventStatus;
import com.jay.getinline.domain.Event;
import com.jay.getinline.domain.QEvent;
import com.jay.getinline.dto.EventViewResponse;
import com.jay.getinline.exception.GeneralException;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class EventRepositoryCustomImpl extends QuerydslRepositorySupport implements EventRepositoryCustom {


    public EventRepositoryCustomImpl() {
        super(Event.class);
    }

    @Override
    public Page<EventViewResponse> findEventViewPageBySearchParams (
            String placeName,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime,
            Pageable pageable
    ) {
        QEvent event = QEvent.event;

        JPQLQuery<EventViewResponse> query = from(event)
                .select(Projections.constructor(
                        EventViewResponse.class,
                        event.id,
                        event.place.placeName,
                        event.eventName,
                        event.eventStatus,
                        event.eventStartDatetime,
                        event.eventEndDatetime,
                        event.currentNumberOfPeople,
                        event.capacity,
                        event.memo
                ));

        if (placeName != null && !placeName.isBlank()) {
            query.where(event.place.placeName.containsIgnoreCase(placeName));
        }
        if (eventName != null && !eventName.isBlank()) {
            query.where(event.eventName.containsIgnoreCase(eventName));
        }
        if (eventStatus != null) {
            query.where(event.eventStatus.eq(eventStatus));
        }
        if (eventStartDateTime != null) {
            query.where(event.eventStartDatetime.goe(eventStartDateTime));
        }
        if (eventEndDateTime != null) {
            query.where(event.eventEndDatetime.loe(eventEndDateTime));
        }

        List<EventViewResponse> events = Optional.ofNullable(getQuerydsl())
                .orElseThrow(() -> new GeneralException(ErrorCode.DATA_ACCESS_ERROR, "Spring Data JPA로부터 Querydsl 인스턴스를 가져오는데 실패함."))
                .applyPagination(pageable, query)
                .fetch();

        return new PageImpl<>(events, pageable, query.fetchCount());
    }
}
