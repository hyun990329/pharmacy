package com.my.phamacy.service;

import com.my.phamacy.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class KakaoCategorySearchService {
    private final RestTemplate restTemplate;

    // 환경변수 ${KAKAO_REST_API_KEY} 값을 가져와서 사용
    @Value("${KAKAO_REST_API_KEY}")
    private String kakaoRestApiKey;

    // 우리가 만들 주소(url)
    // https://dapi.kakao.com/v2/local/search/address.json?query=강남대로 405
    private static final String KAKAO_CATEGORY_URL = "https://dapi.kakao.com/v2/local/search/category.json";

    public KakaoApiResponseDto resultCategorySearch(
            double latitude, double longitude, double radius) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(KAKAO_CATEGORY_URL);
        // 파라미터 설정
        uriBuilder.queryParam("category_group_code", "PM9");
        uriBuilder.queryParam("x", longitude);
        uriBuilder.queryParam("y", latitude);
        uriBuilder.queryParam("radius", radius);
        uriBuilder.queryParam("sort", "distance");

        URI uri = uriBuilder.build().encode().toUri();

        // 헤더 만들기
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION,
                "KakaoAK " + kakaoRestApiKey);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                KakaoApiResponseDto.class
        ).getBody();
    }
}
