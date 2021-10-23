package com.behl.dolores.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.behl.dolores.constant.ApiConstant;
import com.behl.dolores.dto.SearchResponseDto;
import com.behl.dolores.service.MasterHouseService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class MasterHouseController {

    private final MasterHouseService masterHouseService;

    @GetMapping(value = ApiConstant.HOUSES, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<SearchResponseDto> masterHouseRetreivalHandler(
            @RequestParam(name = ApiConstant.QUERY, required = false) final String query,
            @RequestParam(name = ApiConstant.PAGE, required = false) final Integer page,
            @RequestParam(name = ApiConstant.COUNT, required = false) final Integer count) {
        return ResponseEntity.ok(masterHouseService.retreive(query, page, count));
    }

}