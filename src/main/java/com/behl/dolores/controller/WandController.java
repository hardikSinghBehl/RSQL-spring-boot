package com.behl.dolores.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.behl.dolores.constant.ApiConstant;
import com.behl.dolores.entity.Wand;
import com.behl.dolores.service.WandService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class WandController {

    private final WandService wandService;

    @GetMapping(value = ApiConstant.WANDS, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<Wand>> wandsRetreivalHandler(
            @RequestParam(name = ApiConstant.QUERY, required = false) final String query) {
        return ResponseEntity.ok(wandService.retreive(query));
    }

}