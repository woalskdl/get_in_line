package com.jay.getinline.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QEventCommon is a Querydsl query type for EventCommon
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QEventCommon extends BeanPath<EventCommon> {

    private static final long serialVersionUID = -81223469L;

    public static final QEventCommon eventCommon = new QEventCommon("eventCommon");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public QEventCommon(String variable) {
        super(EventCommon.class, forVariable(variable));
    }

    public QEventCommon(Path<? extends EventCommon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEventCommon(PathMetadata metadata) {
        super(EventCommon.class, metadata);
    }

}

