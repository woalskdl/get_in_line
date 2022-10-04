package com.jay.getinline.controller.api;

import com.jay.getinline.dto.APIDataResponse;
import com.jay.getinline.dto.AdminRequest;
import com.jay.getinline.dto.LoginRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring Data REST 로 API 를 만들어서 당장 필요가 없어진 컨트롤러.
 * 우선 deprecated 하고, 향후 사용 방안을 고민해 본다.
 * 필요에 따라서는 다시 살릴 수도 있음
 *
 * @deprecated 0.1.2
 */
@Deprecated
//@RequestMapping("/api")
//@RestController
public class APIAuthController {

    @GetMapping("/sign-up")
    public APIDataResponse<String> signUp(@RequestBody AdminRequest adminRequest) {
        return APIDataResponse.empty();
    }

    @GetMapping("login")
    public APIDataResponse<String> login(@RequestBody LoginRequest loginRequest) {
        return APIDataResponse.empty();
    }
}
