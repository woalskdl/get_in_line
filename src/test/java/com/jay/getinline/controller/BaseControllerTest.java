package com.jay.getinline.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
//@AutoConfigureMockMvc
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@SpringBootTest
@WebMvcTest(BaseController.class) // 이거 쓰면 @AutoConfigureMockMvc 없어도 됨 (컨트롤러 테스트) / 괄호없으면 전체 클래스 스캔
class BaseControllerTest {

    // 테스트 실행 전 수행할 메소드
//    @BeforeEach
//    void setUp() {
//    }

    // 테스트 실행 후 수행할 메소드
//    @AfterEach
//    void tearDown() {
//    }

    // 클래스에 @AutoConfigureMockMvc 붙여서 Autowired 가능
    // 또는 메소드 파라미터로 직접 Autowired 가능
//    @Autowired
    private final MockMvc mvc;

    // 또는 생성자로 주입 가능
//    @Autowired
    public BaseControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    // 또는 클래스 단위로 AutowireMode.ALL 로 설정해서 @Autowired 생략 가능 (상단 참조)

    @DisplayName("[view][GET] 기본 페이지 요청")
    @Test
//    void basePageShouldShowIndexPage() throws Exception {
    void givenNothing_whenRequestingRootPage_thenReturnsIndexPage() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE))
                .andExpect(content().string(containsString("This is default page.")))
                .andExpect(view().name("index"))
                .andDo(print());
    }
}