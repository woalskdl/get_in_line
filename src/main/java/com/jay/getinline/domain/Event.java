package com.jay.getinline.domain;

import com.jay.getinline.constant.EventStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

// -- @Entity 에서 사용되는 주요 JPA 어노테이션
// @Table, @Index, @UniqueConstraint : 테이블 기본 정보와 인덱스, unique 키 설정
// @Id, @GeneratedValue : primary key 설정
// @Column : 각 컬럼 설정 - 기본적으로 세팅되나 attribute 를 통해 속성을 제어하고 싶을 때
// @Enumerated : enum 을 처리하는 방법을 설정 (Ex. 코드 - OPEN, CLOSE 와 같은 코드값)
// @Transient : 특정 필드를 DB 영속 대상에서 제외 (Ex. DB는 firstName, lastName 따로 관리하나 Entity 에서는 fullName 을 관리하고 싶을 때)
// @OneToOne, @OneToMany, @ManyToOne, @ManyToMany : 연관 관계 설정
// @MappedSuperClass : 상속을 이용한 공통 필드 정의
// @Embedded, @Embeddable : 클래스 멤버를 이용한 공통 필드 정의
// @DateTimeFormat : 스프링에서 제공하는 날짜 입력 포맷 지정하는 annotation

// -- createdAt, modifiedAt 등 기본적으로 들어가는 컬럼에 대해 지정할 수 있음
// @PrePersist
// @PostPersist
// @PreRemove
// @PostRemove
// @PreUpdate
// @PostUpdate
// @PostLoad

// 엔티티의 생성일시, 수정일시, 생성자, 수정자를 자동으로 관리해주는 애노테이션
// @EnableJpaAuditing
// @EntityListeners(AuditingEntityListener.class)
// >> @CreatedBy, @CreatedDate, @LastModifiedBy, @LastModifiedDate

//@MappedSuperclass
@Getter
@ToString
@EqualsAndHashCode
@Table(indexes = {
//        @Index(columnList = "placeId"),
        @Index(columnList = "eventName"),
        @Index(columnList = "eventStartDatetime"),
        @Index(columnList = "eventEndDatetime"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "modifiedAt")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Event
//        extends EventCommon
{

    @Setter(AccessLevel.PRIVATE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
//    @Column(nullable = false)
//    private Long placeId;
    @ManyToOne(optional = false)
    private Place place;

    @Setter
    @Column(nullable = false)
    private String eventName;

    @Setter
    @Column(nullable = false, columnDefinition = "varchar(20) default 'OPENED'")
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;

    @Setter
    @Column(nullable = false, columnDefinition = "datetime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime eventStartDatetime;

    @Setter @Column(nullable = false, columnDefinition = "datetime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime eventEndDatetime;

    @Setter @Column(nullable = false, columnDefinition = "integer default 0")
    private int currentNumberOfPeople;

    @Setter @Column(nullable = false)
    private int capacity;

    @Setter
    private String memo;

    @Column(nullable = false, insertable = false, updatable = false
            // 생략 가능 (아래 @CreatedDate 와 동일함 / 스키마 레벨에서 명확하게 표현하기 위해 씀)
           , columnDefinition = "datetime default CURRENT_TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    // insertable, updatetable : 스키마 insert, update 할때 컬럼이 포함되지 않는다.
    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    // @MappedSuperClass 와 비슷하나 상속관계에 유연하게 사용함
//    @Embedded
//    private EventCommon eventCommon;

    protected Event() {}

    protected Event(
            Place place,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        this.place = place;
        this.eventName = eventName;
        this.eventStatus = eventStatus;
        this.eventStartDatetime = eventStartDatetime;
        this.eventEndDatetime = eventEndDatetime;
        this.currentNumberOfPeople = currentNumberOfPeople;
        this.capacity = capacity;
        this.memo = memo;
    }

    public static Event of(
            Place place,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        return new Event(
                place,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime,
                currentNumberOfPeople,
                capacity,
                memo
        );
    }
}
