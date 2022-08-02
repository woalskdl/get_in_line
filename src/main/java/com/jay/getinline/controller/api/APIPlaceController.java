package com.jay.getinline.controller.api;

import com.jay.getinline.constant.PlaceType;
import com.jay.getinline.dto.APIDataResponse;
import com.jay.getinline.dto.PlaceDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class APIPlaceController {

    @GetMapping("/places")
    public APIDataResponse<List<PlaceDTO>> getPlaces() {
        return APIDataResponse.of(List.of(PlaceDTO.of(
                PlaceType.COMMON,
                "실내 배드민턴장",
                "서울시 강남구 도곡동 27-1",
                "010-4541-7718",
                30,
                "오픈 이벤트"
        )));
    }

    @PostMapping("/places")
    public Boolean createPlace() {
        return true;
    }

    @GetMapping("/places/{placeId}")
    public APIDataResponse<PlaceDTO> getPlace(@PathVariable Integer placeId) {
        // 테스트를 위한 임시 분기 (placeId 가 없을 경우)
        if(placeId == 2)
            return APIDataResponse.of(null);

        return APIDataResponse.of(PlaceDTO.of(
                PlaceType.COMMON,
                "실내 배드민턴장",
                "서울시 강남구 도곡동 27-1",
                "010-4541-7718",
                30,
                "오픈 이벤트"
        ));
    }

    @PutMapping("/places/{placeId}")
    public Boolean modifyPlace(@PathVariable Integer placeId) {
        return true;
    }

    @DeleteMapping("/places/{placeId}")
    public Boolean removePlace(@PathVariable Integer placeId) {
        return true;
    }

}
