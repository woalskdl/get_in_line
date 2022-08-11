package com.jay.getinline.dto;

import com.jay.getinline.constant.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class APIDataResponseTest {

    @Test
    @DisplayName("문자열 데이터가 주어지면, 표준 성공 응답을 생성한다.")
    void givenStringData_whenCreatingResponse_thenReturnsSuccessfulReponse() {
        // Given
        String data = "test data";

        // When
        APIDataResponse<String> response = APIDataResponse.of(data);

        // Then
        assertThat(response)
//                .isNotNull()
                .hasFieldOrPropertyWithValue("success", true)   // null check 또한 포함
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.OK.getCode())
                .hasFieldOrPropertyWithValue("message", ErrorCode.OK.getMessage())
                .hasFieldOrPropertyWithValue("data", data);
    }

    @Test
    @DisplayName("문자열 데이터가 주어지면, 표준 성공 응답을 생성한다. / 리팩토링")
    void givenNothing_whenCreatingResponse_thenReturnsSuccessfulReponse() {
        // Given
//        String data = "test data";

        // When
        APIDataResponse<Null> response = APIDataResponse.empty();

        // Then
        assertThat(response)
//                .isNotNull()
                .hasFieldOrPropertyWithValue("success", true)   // null check 또한 포함
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.OK.getCode())
                .hasFieldOrPropertyWithValue("message", ErrorCode.OK.getMessage())
                .hasFieldOrPropertyWithValue("data", null);
    }
}