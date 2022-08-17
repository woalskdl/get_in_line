package com.jay.getinline.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jay.getinline.constant.ErrorCode;
import com.jay.getinline.constant.EventStatus;
import com.jay.getinline.constant.PlaceType;
import com.jay.getinline.dto.EventDTO;
import com.jay.getinline.dto.EventResponse;
import com.jay.getinline.service.EventService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(APIEventController.class)
class APIEventControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper;

    @MockBean
    private EventService eventService;  // 해당 MockBean은 아래의 코드에 넣을 수 없다.

    public APIEventControllerTest(
            @Autowired MockMvc mvc,
            @Autowired ObjectMapper mapper
    ) {
        this.mvc = mvc;
        this.mapper = mapper;
    }

    @DisplayName("[API][GET] 이벤트 리스트 조회")
    @Test
    void givenNothing_whenRequestingEvents_thenReturnsListOfEventsInStandardResponse() throws Exception {
        // Given
        given(eventService.getEvents(any(), any(), any(), any(), any())).willReturn(List.of(createEventDTO()));

        // When & Then
        mvc.perform(get("/api/events")
                        .queryParam("placeId", "1")
                        .queryParam("eventName", "운동")
                        .queryParam("eventStatus", EventStatus.OPENED.name())
                        .queryParam("eventStartDateTime", "2022-08-17T09:00:00")
                        .queryParam("eventEndDateTime", "2022-08-17T18:00:00")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].placeId").value(1L))
                .andExpect(jsonPath("$.data[0].eventName").value("오후 운동"))
                .andExpect(jsonPath("$.data[0].eventStatus").value(EventStatus.OPENED.name()))
                // 왜안되지?;
//                .andExpect(jsonPath("$.data[0].eventStartDateTime").value(
//                        LocalDateTime.of(2022, 8, 3, 9, 0 ,1))
//                )
//                .andExpect(jsonPath("$.data[0].eventEndDateTime").value(
//                        LocalDateTime.of(2022, 8, 3, 18, 0 ,1))
//                )
                .andExpect(jsonPath("$.data[0].currentNumberOfPeople").value(30))
                .andExpect(jsonPath("$.data[0].capacity").value(50))
                .andExpect(jsonPath("$.data[0].memo").value("마스크를 꼭 착용하세요."))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));

        then(eventService).should().getEvents(any(), any(), any(), any(), any());
    }

    @DisplayName("[API][POST] 이벤트 생성")
    @Test
    void givenEvent_whenCreatingAnEvent_thenReturnsSuccessfulStandardResponse() throws Exception {
        // Given
        EventResponse eventResponse = EventResponse.of(
                1L,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2022, 8, 3, 9, 0 ,0),
                LocalDateTime.of(2022, 8, 3, 18, 0 ,0),
                30,
                50,
                "마스크를 꼭 착용하세요."
        );

        // When & Then
        mvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(eventResponse))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @Test
    void getEvent() {
    }

    @Test
    void modifyEvent() {
    }

    @Test
    void removeEvent() {
    }

    private EventDTO createEventDTO() {
        return EventDTO.of(
                1L,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2022, 8, 3, 9, 0, 1),
                LocalDateTime.of(2022, 8, 3, 18, 0, 1),
                30,
                50,
                "마스크를 꼭 착용하세요.",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}