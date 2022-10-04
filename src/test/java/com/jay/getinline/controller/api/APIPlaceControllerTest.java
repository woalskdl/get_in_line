package com.jay.getinline.controller.api;

import com.jay.getinline.constant.ErrorCode;
import com.jay.getinline.constant.PlaceType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Deprecated
@Disabled("API 컨트롤러가 필요없는 상황이어서 비활성화")
@DisplayName("API 컨트롤러 - 장소")
@WebMvcTest(APIPlaceController.class)
class APIPlaceControllerTest {
    private final MockMvc mvc;

    public APIPlaceControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[API][GET] 장소 리스트 조회")
    @Test
    void givenNothing_whenRequestingPlaces_thenReturnsListOfPlacesInStandardResponse() throws Exception {
        // Given

        // When & Then
        // Json 문법 활용 (Json Path - https://github.com/json-path/JsonPath 참조)
        mvc.perform(get("/api/places"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].placeType").value(PlaceType.COMMON.name()))
                .andExpect(jsonPath("$.data[0].placeName").value("실내 배드민턴장"))
                .andExpect(jsonPath("$.data[0].address").value("서울시 강남구 도곡동 27-1"))
                .andExpect(jsonPath("$.data[0].phoneNumber").value("010-4541-7718"))
                .andExpect(jsonPath("$.data[0].capacity").value(30))
                .andExpect(jsonPath("$.data[0].memo").value("오픈 이벤트"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API][GET] 장소 리스트 조회 - 장소 리스트 데이터를 담은 표준 API 출력")
    @Test
    void givenNothing_whenRequestingPlaces_thenReturnsPlacesInStandardResponse() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/places"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].placeType").value(PlaceType.COMMON.name()))
                .andExpect(jsonPath("$.data[0].placeName").value("실내 배드민턴장"))
                .andExpect(jsonPath("$.data[0].address").value("서울시 강남구 도곡동 27-1"))
                .andExpect(jsonPath("$.data[0].phoneNumber").value("010-4541-7718"))
                .andExpect(jsonPath("$.data[0].capacity").value(30))
                .andExpect(jsonPath("$.data[0].memo").value("오픈 이벤트"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API][GET] 장소 조회 - 장소 있는 경우")
    @Test
    void givenPlaceAndId_whenRequestingPlace_thenReturnsPlaceInStandardResponse() throws Exception {
        // Given
        int placeId = 1;

        // When & Then
        mvc.perform(get("/api/places/" + placeId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.placeType").value(PlaceType.COMMON.name()))
                .andExpect(jsonPath("$.data.placeName").value("실내 배드민턴장"))
                .andExpect(jsonPath("$.data.address").value("서울시 강남구 도곡동 27-1"))
                .andExpect(jsonPath("$.data.phoneNumber").value("010-4541-7718"))
                .andExpect(jsonPath("$.data.capacity").value(30))
                .andExpect(jsonPath("$.data.memo").value("오픈 이벤트"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API][GET] 장소 조회 - 장소 없는 경우")
    @Test
    void givenPlaceAndId_whenRequestingPlace_thenReturnsEmptyInStandardResponse() throws Exception {
        // Given
        int placeId = 2;

        // When & Then
        mvc.perform(get("/api/places/" + placeId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

}