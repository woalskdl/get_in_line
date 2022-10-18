package com.jay.getinline.service;

import com.jay.getinline.constant.ErrorCode;
import com.jay.getinline.constant.EventStatus;
import com.jay.getinline.constant.PlaceType;
import com.jay.getinline.domain.Event;
import com.jay.getinline.domain.Place;
import com.jay.getinline.dto.EventDTO;
import com.jay.getinline.exception.GeneralException;
import com.jay.getinline.repository.EventRepository;
import com.jay.getinline.repository.PlaceRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @InjectMocks
    private EventService sut;

    @Mock private EventRepository eventRepository;
    @Mock private PlaceRepository placeRepository;

    @DisplayName("이벤트를 검색하면, 결과를 출력하여 보여준다.")
    @Test
    void givenNothing_whenSearchingEvents_thenReturnEntireEventList() {
        // Given
        given(eventRepository.findAll(any(Predicate.class)))
                .willReturn(List.of(
                        createEvent("오전 운동", true),
                        createEvent("오후 운동", false)
                ));

        // When
        List<EventDTO> list = sut.getEvents(new BooleanBuilder());

        // Then
        assertThat(list).hasSize(2);
        then(eventRepository).should().findAll(any(Predicate.class));
    }

    @DisplayName("이벤트를 검색하는데 에러가 발생할 경우, 줄서기 프로젝트 기본 에러로 전환하여 예외를 출력한다.")
    @Test
    void givenDataRelatedException_whenSearchingEvents_thenReturnGeneralException() {
        // Given
        RuntimeException e = new RuntimeException("This is test.");
        given(eventRepository.findAll(any(Predicate.class))).willThrow(e);

        // When
        Throwable thrown = catchThrowable(() -> sut.getEvents(new BooleanBuilder()));

        // Then
        assertThat(thrown)
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
        then(eventRepository).should().findAll(any(Predicate.class));
    }

    @DisplayName("이벤트 ID로 존재하는 이벤트를 조회하면, 해당 이벤트 정보를 출력하여 보여준다.")
    @Test
    void givenEvnetId_whenSearchingExistingEvent_thenReturnsEvent() {
        // Given
        long eventId = 1L;
        Event event = createEvent("오전 운동", true);
        given(eventRepository.findById(eventId)).willReturn(Optional.of(event));

        // When
        Optional<EventDTO> result = sut.getEvent(eventId);

        // Then
        assertThat(result).hasValue(EventDTO.of(event));
        then(eventRepository).should().findById(eventId);
    }

    @DisplayName("이벤트 ID로 존재하는 이벤트를 조회하면, 빈 정보를 출력하여 보여준다.")
    @Test
    void givenEvnetId_whenSearchingNoneExistEvent_thenReturnsEmptyOptional() {
        // Given
        long eventId = 1L;
        given(eventRepository.findById(eventId)).willReturn(Optional.empty());

        // When
        Optional<EventDTO> result = sut.getEvent(eventId);

        // Then
        assertThat(result).isEmpty();
        then(eventRepository).should().findById(eventId);
    }

    @DisplayName("이벤트 ID로 이벤트를 검색하는데 에러가 발생할 경우, 줄서기 프로젝트 기본 에러로 전환하여 예외를 출력한다.")
    @Test
    void givenDataRelatedException_whenSearchingEvent_thenReturnGeneralException() {
        // Given
        RuntimeException e = new RuntimeException("This is test.");
        given(eventRepository.findById(any())).willThrow(e);

        // When
        Throwable throwable = catchThrowable(() -> sut.getEvent(null));

        // Then
        assertThat(throwable)
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
        then(eventRepository).should().findById(any());
    }

    @DisplayName("이벤트 정보를 주면 이벤트를 생성하고 결과를 true로 보여준다.")
    @Test
    void givenEvent_whenCreating_thenCreatesEventAndReturnsTrue() {
        // Given
        EventDTO eventDTO = EventDTO.of(createEvent("오후 운동", false));
        given(placeRepository.findById(eventDTO.placeDTO().id())).willReturn(Optional.of(createPlace()));
        given(eventRepository.save(any(Event.class))).willReturn(any());

        // When
        boolean result = sut.createEvent(eventDTO);

        // Then
        assertThat(result).isTrue();
        then(placeRepository).should().findById(eventDTO.placeDTO().id());
        then(eventRepository).should().save(any(Event.class));
    }

    @DisplayName("이벤트 정보를 주지 않으면 이벤트를 생성하고 결과를 false로 보여준다.")
    @Test
    void givenNothing_whenCreating_thenAbortCreatingAndReturnsFalse() {
        // Given

        // When
        boolean result = sut.createEvent(null);

        // Then
        assertThat(result).isFalse();
        then(placeRepository).shouldHaveNoInteractions();
        then(eventRepository).shouldHaveNoInteractions();
    }

    @DisplayName("이벤트 생성 중 장소 정보가 틀리거나 없으면, 줄서기 프로젝트 기본 에러로 전환하여 예외 던진다")
    @Test
    void givenWrongPlaceId_whenCreating_thenThrowsGeneralException() {
        // Given
        Event event = createEvent(null, false);
        given(placeRepository.findById(event.getPlace().getId())).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> sut.createEvent(EventDTO.of(event)));

        // Then
        assertThat(thrown)
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
        then(placeRepository).should().findById(event.getPlace().getId());
        then(eventRepository).shouldHaveNoInteractions();
    }

    @DisplayName("이벤트 생성 중 데이터 예외가 발생하면, 줄서기 프로젝트 기본 에러로 전환하여 예외 던진다")
    @Test
    void givenDataRelatedException_whenCreating_thenThrowsGeneralException() {
        // Given
        Event event = createEvent(null, false);
        RuntimeException e = new RuntimeException("This is test.");
        given(placeRepository.findById(event.getPlace().getId())).willReturn(Optional.of(createPlace()));
        given(eventRepository.save(any())).willThrow(e);

        // When
        Throwable thrown = catchThrowable(() -> sut.createEvent(EventDTO.of(event)));
        // Then
        assertThat(thrown)
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
        then(placeRepository).should().findById(event.getPlace().getId());
        then(eventRepository).should().save(any());
    }

    @DisplayName("이벤트 ID와 정보를 주면, 이벤트 정보를 변경하고 결과를 true로 보여준다.")
    @Test
    void givenEventIdAndItsInfo_whenModifying_thenModifiesEventAndReturnsTrue() {
        // Given
        long eventId = 1L;
        Event originalEvent = createEvent("오후 운동", false);
        Event changedEvent = createEvent("오전 운동", true);
        given(eventRepository.findById(eventId)).willReturn(Optional.of(originalEvent));
        given(eventRepository.save(changedEvent)).willReturn(changedEvent);

        // When
        boolean result = sut.modifyEvent(eventId, EventDTO.of(changedEvent));

        // Then
        assertThat(result).isTrue();
        assertThat(originalEvent.getEventName()).isEqualTo(changedEvent.getEventName());
        assertThat(originalEvent.getEventStartDatetime()).isEqualTo(changedEvent.getEventStartDatetime());
        assertThat(originalEvent.getEventEndDatetime()).isEqualTo(changedEvent.getEventEndDatetime());
        assertThat(originalEvent.getEventStatus()).isEqualTo(changedEvent.getEventStatus());
        then(eventRepository).should().findById(eventId);
        then(eventRepository).should().save(changedEvent);
    }

    @DisplayName("이벤트 ID를 주지 않으면, 이벤트 정보 변경을 중단하고 결과를 false로 보여준다.")
    @Test
    void givenNoEventId_whenModifying_thenAbortModifyingAndReturnsFalse() {
        // Given
        Event event = createEvent("오후 운동", false);


        // When
        boolean result = sut.modifyEvent(null, EventDTO.of(event));

        // Then
        assertThat(result).isFalse();
        then(eventRepository).shouldHaveNoInteractions();
    }

    @DisplayName("이벤트 ID를 주고 변경할 정보를 주지 않으면, 이벤트 정보 변경을 중단하고 결과를 false로 보여준다.")
    @Test
    void givenEventIdOnly_whenModifying_thenAbortModifyingAndReturnsFalse() {
        // Given
        long eventId = 1L;

        // When
        boolean result = sut.modifyEvent(eventId, null);

        // Then
        assertThat(result).isFalse();
        then(eventRepository).shouldHaveNoInteractions();
    }

    @DisplayName("이벤트 변경 중 데이터 오류가 발생하면, 줄서기 프로젝트 기본 에러로 전환하여 예외 던진다.")
    @Test
    void givenDataRelatedException_whenModifying_thenThrowsGeneralException() {
        // Given
        long eventId = 1L;
        Event originalEvent = createEvent("오후 운동", false);
        Event wrongEvent = createEvent(null, false);
        RuntimeException e = new RuntimeException("This is test.");
        given(eventRepository.findById(eventId)).willReturn(Optional.of(originalEvent));
        given(eventRepository.save(any())).willThrow(e);

        // When
        Throwable thrown = catchThrowable(() -> sut.modifyEvent(eventId, EventDTO.of(wrongEvent)));

        // Then
        assertThat(thrown)
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
        then(eventRepository).should().findById(eventId);
        then(eventRepository).should().save(any());
    }

    @DisplayName("이벤트 ID를 주면 이벤트 정보를 삭제하고 결과를 true로 보여준다.")
    @Test
    void givenEventId_whenDeleting_thenDeleteEventAndReturnsTrue() {
        // Given
        long eventId = 1L;
        willDoNothing().given(eventRepository).deleteById(eventId);

        // When
        boolean result = sut.removeEvent(eventId);

        // Then
        assertThat(result).isTrue();
        then(eventRepository).should().deleteById(eventId);

    }

    @DisplayName("이벤트 ID를 주지 않으면 이벤트 정보 삭제를 중단하고 결과를 false로 보여준다.")
    @Test
    void givenNothing_whenDeleting_thenAbortDeleteEventAndReturnsFalse() {
        // Given

        // When
        boolean result = sut.removeEvent(null);

        // Then
        assertThat(result).isFalse();
        then(eventRepository).shouldHaveNoInteractions();

    }

    @DisplayName("이벤트 삭제 중 데이터 오류가 발생하면, 줄서기 프로젝트 기본 에러로 전환하여 예외 던진다.")
    @Test
    void givenDataRelatedException_whenDeleting_thenThrowsGeneralException() {
        // Given
        long eventId = 0L;
        RuntimeException e = new RuntimeException("This is test.");
        willThrow(e).given(eventRepository).deleteById(eventId);

        // When
        Throwable thrown = catchThrowable(() -> sut.removeEvent(eventId));

        // Then
        assertThat(thrown)
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
        then(eventRepository).should().deleteById(eventId);
    }

    private Event createEvent(
            long id,
            long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime
    ) {
        Event event =  Event.of(
                        createPlace(placeId),
                        eventName,
                        eventStatus,
                        eventStartDateTime,
                        eventEndDateTime,
                        0,
                        24,
                        "마스크 꼭 착용하세요"
        );

        ReflectionTestUtils.setField(event, "id", id);

        return event;
    }

    private Event createEvent(
            String eventName,
            boolean isMorning
    ) {
        String hourStart = isMorning ? "09" : "13";
        String hourEnd = isMorning ? "12" : "16";

        return createEvent(
                1L,
                1L,
                eventName,
                EventStatus.OPENED,
                LocalDateTime.parse("2022-08-16T%s:00:00".formatted(hourStart)),
                LocalDateTime.parse("2022-08-16T%s:00:00".formatted(hourEnd))
        );
    }

    private Place createPlace() {
        return createPlace(1L);
    }

    private Place createPlace(long id) {
        Place place = Place.of(PlaceType.COMMON, "test place", "test address", "010-1234-1234", 10, null);
        ReflectionTestUtils.setField(place, "id", id);

        return place;
    }
}