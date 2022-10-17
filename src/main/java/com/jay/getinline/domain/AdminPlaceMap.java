package com.jay.getinline.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@Table(indexes = {
//        @Index(columnList = "adminId"),
//        @Index(columnList = "placeId"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "modifiedAt")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class AdminPlaceMap {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
//    @Column(nullable = false)
//    private Long adminId;
    @ManyToOne(optional = false)
    private Admin admin;

    @Setter
//    @Column(nullable = false)
//    private Long placeId;
    @ManyToOne(optional = false)
    private Place place;

    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    protected AdminPlaceMap() {}

//    protected AdminPlaceMap(Long adminId, Long placeId) {
//        this.adminId = adminId;
//        this.placeId = placeId;
//    }
    protected AdminPlaceMap(Admin admin, Place place) {
        this.admin = admin;
        this.place = place;
    }

    public static AdminPlaceMap of(Admin admin, Place place) {
        return new AdminPlaceMap(admin, place);
    }
}
