package com.behl.dolores.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.behl.dolores.entity.Wand;
import com.behl.dolores.repository.WandRepository;
import com.behl.dolores.rsql.CustomRsqlVisitor;

import cz.jirutka.rsql.parser.RSQLParser;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WandService {

    private final WandRepository wandRepository;
    private final RSQLParser rsqlParser;
    private final CustomRsqlVisitor<Wand> wandRsqlVisitor = new CustomRsqlVisitor<Wand>();

    public List<Wand> retreive(final String query) {
        if (query == null || query.length() == 0)
            return wandRepository.findAll();

        Specification<Wand> specification = rsqlParser.parse(query).accept(wandRsqlVisitor);
        return wandRepository.findAll(specification);
    }

}