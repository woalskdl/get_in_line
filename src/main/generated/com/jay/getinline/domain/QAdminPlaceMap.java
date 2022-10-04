package com.jay.getinline.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdminPlaceMap is a Querydsl query type for AdminPlaceMap
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAdminPlaceMap extends EntityPathBase<AdminPlaceMap> {

    private static final long serialVersionUID = -1983493262L;

    public static final QAdminPlaceMap adminPlaceMap = new QAdminPlaceMap("adminPlaceMap");

    public final NumberPath<Long> adminId = createNumber("adminId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> placeId = createNumber("placeId", Long.class);

    public QAdminPlaceMap(String variable) {
        super(AdminPlaceMap.class, forVariable(variable));
    }

    public QAdminPlaceMap(Path<? extends AdminPlaceMap> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdminPlaceMap(PathMetadata metadata) {
        super(AdminPlaceMap.class, metadata);
    }

}

