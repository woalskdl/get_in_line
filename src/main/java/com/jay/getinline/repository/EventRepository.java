package com.jay.getinline.repository;

import com.jay.getinline.constant.EventStatus;
import com.jay.getinline.domain.Event;
import com.jay.getinline.domain.QEvent;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends
        JpaRepository<Event, Long>,
        // 아래 extneds 함으로써 readonly 기능만 구현하는 repository가 됨
//        EventReadOnlyRepository<Event, Long>,
        QuerydslPredicateExecutor<Event>,
        QuerydslBinderCustomizer<QEvent> {

    // 아래 쿼리와 동일
//    @Query("select e from Event e where e.eventName = :eventName and e.eventStatus = :eventStatus")
    List<Event> findByEventNameAndEventStatus(String eventName, EventStatus eventStatus);
//    @Param("") >> 순서에 상관없이 Parameter 넣을 수 있음.
    // findByEventNameAndEventStatus(null, null) >> select e from Event e where event_name is null and event_status is null

    Optional<Event> findFirstByEventEndDatetimeBetween(LocalDateTime from, LocalDateTime to);

    @Override
    default void customize(QuerydslBindings bindings, QEvent root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.placeId, root.eventName, root.eventStatus, root.eventStartDatetime, root.eventEndDatetime);
        bindings.bind(root.eventName).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.eventStartDatetime).first(ComparableExpression::goe);
        bindings.bind(root.eventEndDatetime).first(ComparableExpression::loe);
    }
}
