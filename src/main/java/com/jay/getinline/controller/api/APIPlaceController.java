package com.jay.getinline.controller.api;

import com.jay.getinline.constant.PlaceType;
import com.jay.getinline.dto.APIDataResponse;
import com.jay.getinline.dto.PlaceRequest;
import com.jay.getinline.dto.PlaceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
public class APIPlaceController {

    @GetMapping("/places")
    public APIDataResponse<List<PlaceResponse>> getPlaces() {
        return APIDataResponse.of(List.of(PlaceResponse.of(
                PlaceType.COMMON,
                "실내 배드민턴장",
                "서울시 강남구 도곡동 27-1",
                "010-4541-7718",
                30,
                "오픈 이벤트"
        )));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/places")
    public APIDataResponse<Void> createPlace(@RequestBody PlaceRequest placeRequest) {
        return APIDataResponse.empty();
    }

    @GetMapping("/places/{placeId}")
    public APIDataResponse<PlaceResponse> getPlace(@PathVariable Integer placeId) {
        // 테스트를 위한 임시 분기 (placeId 가 없을 경우)
        if(placeId == 2)
            return APIDataResponse.of(null);

        return APIDataResponse.of(PlaceResponse.of(
                PlaceType.COMMON,
                "실내 배드민턴장",
                "서울시 강남구 도곡동 27-1",
                "010-4541-7718",
                30,
                "오픈 이벤트"
        ));
    }

    @PutMapping("/places/{placeId}")
    public APIDataResponse<Void> modifyPlace(
            @PathVariable Long placeId,
            @RequestBody PlaceRequest placeRequest
    ) {
        return APIDataResponse.empty();
    }

    @DeleteMapping("/places/{placeId}")
    public APIDataResponse<Void> removePlace(@PathVariable Long placeId) {
        return APIDataResponse.empty();
    }

}
