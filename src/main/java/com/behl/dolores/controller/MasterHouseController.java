package com.behl.dolores.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.behl.dolores.entity.MasterHouse;
import com.behl.dolores.service.MasterHouseService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class MasterHouseController {

    private final MasterHouseService masterHouseService;

    @GetMapping(value = "/houses", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<MasterHouse>> masterHouseRetreivalHandler(
            @RequestParam(name = "query", required = false) final String query) {
        return ResponseEntity.ok(masterHouseService.retreive(query));
    }

}