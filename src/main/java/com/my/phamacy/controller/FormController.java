package com.my.phamacy.controller;

import com.my.phamacy.dto.DocumentDto;
import com.my.phamacy.dto.InputDto;
import com.my.phamacy.dto.KakaoApiResponseDto;
import com.my.phamacy.dto.OutputDto;
import com.my.phamacy.service.KakaoAddressSearchService;
import com.my.phamacy.service.KakaoCategorySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class FormController {
    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final KakaoCategorySearchService kakaoCategorySearchService;

    public FormController(KakaoAddressSearchService kakaoAddressSearchService,
                          KakaoCategorySearchService kakaoCategorySearchService) {
        this.kakaoAddressSearchService = kakaoAddressSearchService;
        this.kakaoCategorySearchService = kakaoCategorySearchService;
    }


    @GetMapping("/")
    public String mainForm() {
        return "main";
    }

    @GetMapping("/output")
    public String outputForm() {
        return "output";
    }

    @PostMapping("/search")
    public String searchAddress(InputDto dto, Model model) {
        // 주소 검색 API 호출
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService
                .requestAddressSearch(dto.getAddress());
        // 카테고리 서비스 호출을 위해서 documents 배열에서 0번째 것 만 뺀다.
        DocumentDto documentDto = kakaoApiResponseDto
                .getDocumentList()
                .get(0);

        // 카테고리 검색
        double radius = 1000;
        KakaoApiResponseDto recommendationDto =
                kakaoCategorySearchService.resultCategorySearch(
                        documentDto.getLatitude(),
                        documentDto.getLongitude(),
                        radius);
        // recommendationiDto 에서 3개만 추출
        List<OutputDto> outputDtoList =
                kakaoCategorySearchService.makeOutputDto(
                        recommendationDto.getDocumentList());
        System.out.println(outputDtoList);
        model.addAttribute("outputList", outputDtoList);
        return "output";
    }
}