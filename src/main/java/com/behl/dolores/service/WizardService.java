package com.behl.dolores.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.behl.dolores.entity.Wizard;
import com.behl.dolores.repository.WizardRepository;
import com.behl.dolores.repository.rsql.CustomRsqlVisitor;

import cz.jirutka.rsql.parser.RSQLParser;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WizardService {

    private final WizardRepository wizardRepository;
    private final RSQLParser rsqlParser;
    private final CustomRsqlVisitor<Wizard> wizardRsqlVisitor = new CustomRsqlVisitor<Wizard>();

    public List<Wizard> retreive(final String query) {
        if (query == null || query.length() == 0)
            return wizardRepository.findAll();

        Specification<Wizard> specification = rsqlParser.parse(query).accept(wizardRsqlVisitor);
        return wizardRepository.findAll(specification);
    }

}
