package com.jay.getinline.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ErrorCodeTest {

    @ParameterizedTest(name = "[{index}] {0} ===> {1}")
    @MethodSource//("어떤걸 여러개 테스트할지 메소드명을 가져오면 된다. 같으면 생략")
    @DisplayName("예외를 받으면 예외 메세지가 포함된 메세지 출력")
    void givenExceptionWithMessage_whenGettingMessage_thenReturnsMessage(ErrorCode sut, String expected) {
        // Given
        Exception e = new Exception("This is test message");

        // When
        String result = sut.getMessage(e);

        // Then
        assertThat(result).isEqualTo(expected);
    }

    static Stream<Arguments> givenExceptionWithMessage_whenGettingMessage_thenReturnsMessage() {
        return Stream.of(
                arguments(ErrorCode.OK, "OK - This is test message"),
                arguments(ErrorCode.BAD_REQUEST, "bad request - This is test message"),
                arguments(ErrorCode.SPRING_BAD_REQUEST, "Spring-detected bad request - This is test message"),
                arguments(ErrorCode.VALIDATION_ERROR, "Validation error - This is test message"),
                arguments(ErrorCode.INTERNAL_ERROR, "internal error - This is test message"),
                arguments(ErrorCode.SPRING_INTERNAL_ERROR, "Spring-detected bad request - This is test message"),
                arguments(ErrorCode.DATA_ACCESS_ERROR, "Data access error - This is test message")
        );
    }

    @ParameterizedTest(name = "[{index}] \"{0}\" ===> \"{1}\"")
    @MethodSource//("어떤걸 여러개 테스트할지 메소드명을 가져오면 된다. 같으면 생략")
    @DisplayName("예외 메세지를 받으면 해당 예외 메세지 출력")
    void givenMessage_whenGettingMessage_thenReturnsMessage(String input, String expected) {
        // Given

        // When
        String actual = ErrorCode.INTERNAL_ERROR.getMessage(input);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> givenMessage_whenGettingMessage_thenReturnsMessage() {
        return Stream.of(
                arguments(null, ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments("", ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments("   ", ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments("This is test message", "This is test message")
        );
    }

    @DisplayName("toString() 표출 포맷")
    @Test
    void givenErrorCode_whenGettingToString_thenReturnsSimplifiedToString() {
        // Given

        // When
        String result = ErrorCode.INTERNAL_ERROR.toString();

        // Then
        assertThat(result).isEqualTo("INTERNAL_ERROR (2000)");

    }

}
