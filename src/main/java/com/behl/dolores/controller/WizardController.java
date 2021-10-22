package com.behl.dolores.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.behl.dolores.entity.Wizard;
import com.behl.dolores.service.WizardService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class WizardController {

    private final WizardService wizardService;

    @GetMapping(value = "/wizard", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<Wizard>> wizardsRetreivalHandler(
            @RequestParam(name = "query", required = false) final String query) {
        return ResponseEntity.ok(wizardService.retreive(query));
    }

}
