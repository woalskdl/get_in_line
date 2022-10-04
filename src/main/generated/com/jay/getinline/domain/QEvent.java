package com.jay.getinline.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QEvent is a Querydsl query type for Event
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEvent extends EntityPathBase<Event> {

    private static final long serialVersionUID = -1202306872L;

    public static final QEvent event = new QEvent("event");

    public final NumberPath<Integer> capacity = createNumber("capacity", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> currentNumberOfPeople = createNumber("currentNumberOfPeople", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> eventEndDatetime = createDateTime("eventEndDatetime", java.time.LocalDateTime.class);

    public final StringPath eventName = createString("eventName");

    public final DateTimePath<java.time.LocalDateTime> eventStartDatetime = createDateTime("eventStartDatetime", java.time.LocalDateTime.class);

    public final EnumPath<com.jay.getinline.constant.EventStatus> eventStatus = createEnum("eventStatus", com.jay.getinline.constant.EventStatus.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memo = createString("memo");

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> placeId = createNumber("placeId", Long.class);

    public QEvent(String variable) {
        super(Event.class, forVariable(variable));
    }

    public QEvent(Path<? extends Event> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEvent(PathMetadata metadata) {
        super(Event.class, metadata);
    }

}

