package com.behl.dolores.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.behl.dolores.constant.ApiConstant;
import com.behl.dolores.dto.RsqlSearchRequestDto;
import com.behl.dolores.dto.SearchResponseDto;
import com.behl.dolores.service.WandService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class WandController {

    private final WandService wandService;

    @GetMapping(value = ApiConstant.WANDS, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<SearchResponseDto> wandsRetreivalHandler(
            @ModelAttribute final RsqlSearchRequestDto rsqlSearchRequestDto) {
        return ResponseEntity.ok(wandService.retreive(rsqlSearchRequestDto));
    }

}