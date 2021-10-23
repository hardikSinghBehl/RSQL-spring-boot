package com.behl.dolores.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.behl.dolores.entity.MasterHouse;
import com.behl.dolores.repository.MasterHouseRepository;
import com.behl.dolores.rsql.CustomRsqlVisitor;

import cz.jirutka.rsql.parser.RSQLParser;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MasterHouseService {

    private final MasterHouseRepository masterHouseRepository;
    private final RSQLParser rsqlParser;
    private final CustomRsqlVisitor<MasterHouse> masterHouseRsqlVisitor = new CustomRsqlVisitor<MasterHouse>();

    public List<MasterHouse> retreive(final String query) {
        if (query == null || query.length() == 0)
            return masterHouseRepository.findAll();

        Specification<MasterHouse> specification = rsqlParser.parse(query).accept(masterHouseRsqlVisitor);
        return masterHouseRepository.findAll(specification);
    }

}
