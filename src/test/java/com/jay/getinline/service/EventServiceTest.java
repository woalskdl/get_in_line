package com.jay.getinline.service;

import com.jay.getinline.constant.ErrorCode;
import com.jay.getinline.constant.EventStatus;
import com.jay.getinline.dto.EventDTO;
import com.jay.getinline.exception.GeneralException;
import com.jay.getinline.repository.EventRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @InjectMocks
    private EventService sut;

    @Mock
    private EventRepository eventRepository;

    @DisplayName("검색 조건 없이 이벤트 검색하면 전체 결과를 출력하여 보여준다.")
    @Test
    void givenNothing_whenSearchingEvents_thenReturnEntireEventList() {
        // Given
        given(eventRepository.findEvents(null, null, null, null, null))
                .willReturn(List.of(
                        createEventDTO(1L, "오전 운동", true),
                        createEventDTO(1L, "오후 운동", false)
                ));

        // When
        List<EventDTO> list = sut.getEvents(null, null, null, null, null);

        // Then
        assertThat(list).hasSize(2);
//        verify(eventRepository).findEvents(null, null, null, null, null);
        then(eventRepository).should().findEvents(null, null, null, null, null);
    }

    @DisplayName("이벤트를 검색하는데 에러가 발생할 경우, 줄서기 프로젝트 기본 에러로 전환하여 예외를 출력한다.")
    @Test
    void givenDataRelatedException_whenSearchingEvents_thenReturnGeneralException() {
        // Given
        RuntimeException e = new RuntimeException("This is test.");
        given(eventRepository.findEvents(any(), any(), any(), any(), any()))
                .willThrow(e);

        // When
        Throwable throwable = catchThrowable(() -> sut.getEvents(null, null, null, null, null));

        // Then
        assertThat(throwable)
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
        then(eventRepository).should().findEvents(null, null, null, null, null);
    }

    @DisplayName("검색 조건과 함께 이벤트 검색하면 전체 결과를 출력하여 보여준다.")
    @Test
    void givenSearchParams_whenSearchingEvents_thenReturnEventList() {
        // Given
        Long placeId = 1L;
        String eventName = "오후 운동";
        EventStatus eventStatus = EventStatus.OPENED;
        LocalDateTime eventStartDatetime = LocalDateTime.of(2022, 8, 3, 9, 0, 1);
        LocalDateTime eventEndDatetime = LocalDateTime.of(2022, 8, 3, 18, 0, 1);

        given(eventRepository.findEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime))
                .willReturn(List.of(
                        createEventDTO(1L, "오후 운동", eventStatus, eventStartDatetime, eventEndDatetime)
                ));

        // When
        List<EventDTO> list = sut.getEvents(
                placeId,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime
        );

        // Then
        assertThat(list)
                .hasSize(1)
                .allSatisfy(event -> {
                    assertThat(event)
                            .hasFieldOrPropertyWithValue("placeId", placeId)
                            .hasFieldOrPropertyWithValue("eventName", eventName)
                            .hasFieldOrPropertyWithValue("eventStatus", eventStatus);
                    assertThat(event.eventStartDateTime()).isAfterOrEqualTo(eventStartDatetime);
                    assertThat(event.eventEndDateTime()).isBeforeOrEqualTo(eventEndDatetime);
                });

        then(eventRepository).should().findEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime);
    }

    @DisplayName("이벤트 ID로 존재하는 이벤트를 조회하면, 해당 이벤트 정보를 출력하여 보여준다.")
    @Test
    void givenEvnetId_whenSearchingExistingEvent_thenReturnsEvent() {
        // Given
        long eventId = 1L;
        EventDTO eventDTO = createEventDTO(1L, "오전 운동", true);
        given(eventRepository.findEvent(eventId)).willReturn(Optional.of(eventDTO));

        // When
        Optional<EventDTO> result = sut.getEvent(eventId);

        // Then
        assertThat(result).hasValue(eventDTO);
        then(eventRepository).should().findEvent(eventId);
    }

    @DisplayName("이벤트 ID로 존재하는 이벤트를 조회하면, 빈 정보를 출력하여 보여준다.")
    @Test
    void givenEvnetId_whenSearchingNoneExistEvent_thenReturnsEmptyOptional() {
        // Given
        long eventId = 1L;
        given(eventRepository.findEvent(eventId)).willReturn(Optional.empty());

        // When
        Optional<EventDTO> result = sut.getEvent(eventId);

        // Then
        assertThat(result).isEmpty();
        then(eventRepository).should().findEvent(eventId);
    }

    @DisplayName("이벤트 ID로 이벤트를 검색하는데 에러가 발생할 경우, 줄서기 프로젝트 기본 에러로 전환하여 예외를 출력한다.")
    @Test
    void givenDataRelatedException_whenSearchingEvent_thenReturnGeneralException() {
        // Given
        RuntimeException e = new RuntimeException("This is test.");
        given(eventRepository.findEvent(any()))
                .willThrow(e);

        // When
        Throwable throwable = catchThrowable(() -> sut.getEvent(null));

        // Then
        assertThat(throwable)
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
        then(eventRepository).should().findEvent(null);
    }

    @DisplayName("이벤트 정보를 주면 이벤트를 생성하고 결과를 true로 보여준다.")
    @Test
    void givenEvent_whenCreating_thenCreatesEventAndReturnsTrue() {
        // Given
        EventDTO dto = createEventDTO(1L, "오후 운동", false);
        given(eventRepository.insertEvent(dto)).willReturn(true);

        // When
        boolean result = sut.createEvent(dto);

        // Then
        assertThat(result).isTrue();
        then(eventRepository).should().insertEvent(dto);
    }

    @DisplayName("이벤트 정보를 주지 않으면 이벤트를 생성하고 결과를 false로 보여준다.")
    @Test
    void givenNothing_whenCreating_thenAbortCreatingAndReturnsFalse() {
        // Given
        given(eventRepository.insertEvent(null)).willReturn(false);

        // When
        boolean result = sut.createEvent(null);

        // Then
        assertThat(result).isFalse();
        then(eventRepository).should().insertEvent(null);
    }

    @DisplayName("이벤트 ID와 정보를 주면, 이벤트 정보를 변경하고 결과를 true로 보여준다.")
    @Test
    void givenEventIdAndItsInfo_whenModifying_thenModifiesEventAndReturnsTrue() {
        // Given
        long eventId = 1L;
        EventDTO eventDTO = createEventDTO(1L, "오후 운동", false);
        given(eventRepository.updateEvent(eventId, eventDTO)).willReturn(true);

        // When
        boolean result = sut.modifyEvent(eventId, eventDTO);

        // Then
        assertThat(result).isTrue();
        then(eventRepository).should().updateEvent(eventId, eventDTO);
    }

    @DisplayName("이벤트 ID를 주지 않으면, 이벤트 정보 변경을 중단하고 결과를 false로 보여준다.")
    @Test
    void givenNoEventId_whenModifying_thenAbortModifyingAndReturnsFalse() {
        // Given
        EventDTO eventDTO = createEventDTO(1L, "오후 운동", false);
        given(eventRepository.updateEvent(null, eventDTO)).willReturn(false);

        // When
        boolean result = sut.modifyEvent(null, eventDTO);

        // Then
        assertThat(result).isFalse();
        then(eventRepository).should().updateEvent(null, eventDTO);
    }

    @DisplayName("이벤트 ID를 주고 변경할 정보를 주지 않으면, 이벤트 정보 변경을 중단하고 결과를 false로 보여준다.")
    @Test
    void givenEventIdOnly_whenModifying_thenAbortModifyingAndReturnsFalse() {
        // Given
        long eventId = 1L;
        given(eventRepository.updateEvent(eventId, null)).willReturn(false);

        // When
        boolean result = sut.modifyEvent(eventId, null);

        // Then
        assertThat(result).isFalse();
        then(eventRepository).should().updateEvent(eventId, null);
    }

    @DisplayName("이벤트 ID를 주면 이벤트 정보를 삭제하고 결과를 true로 보여준다.")
    @Test
    void givenEventId_whenDeleting_thenDeleteEventAndReturnsTrue() {
        // Given
        long eventId = 1L;
        given(eventRepository.deleteEvent(eventId)).willReturn(true);


        // When
        boolean result = sut.removeEvent(eventId);

        // Then
        assertThat(result).isTrue();
        then(eventRepository).should().deleteEvent(eventId);

    }

    @DisplayName("이벤트 ID를 주지 않으면 이벤트 정보 삭제를 중단하고 결과를 false로 보여준다.")
    @Test
    void givenNothing_whenDeleting_thenAbortDeleteEventAndReturnsFalse() {
        // Given
        given(eventRepository.deleteEvent(null)).willReturn(false);

        // When
        boolean result = sut.removeEvent(null);

        // Then
        assertThat(result).isFalse();
        then(eventRepository).should().deleteEvent(null);

    }

    private EventDTO createEventDTO(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime
    ) {
        return EventDTO.of(
                placeId,
                eventName,
                eventStatus,
                eventStartDateTime,
                eventEndDateTime,
                0,
                24,
                "마스크 꼭 착용하세요",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    private EventDTO createEventDTO(
            long placeId,
            String eventName,
            boolean isMorning
    ) {
        String hourStart = isMorning ? "09" : "13";
        String hourEnd = isMorning ? "12" : "16";

        return createEventDTO(
                placeId,
                eventName,
                EventStatus.OPENED,
                LocalDateTime.parse("2022-08-16T%s:00:00".formatted(hourStart)),
                LocalDateTime.parse("2022-08-16T%s:00:00".formatted(hourEnd))
        );
    }
}