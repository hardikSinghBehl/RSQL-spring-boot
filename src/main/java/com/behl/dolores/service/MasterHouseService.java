package com.behl.dolores.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.behl.dolores.dto.SearchResponseDto;
import com.behl.dolores.entity.MasterHouse;
import com.behl.dolores.repository.MasterHouseRepository;
import com.behl.dolores.rsql.CustomRsqlVisitor;
import com.behl.dolores.utility.PageableUtil;

import cz.jirutka.rsql.parser.RSQLParser;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MasterHouseService {

    private static final Integer DEFAULT_COUNT = 4;

    private final MasterHouseRepository masterHouseRepository;
    private final RSQLParser rsqlParser;
    private final CustomRsqlVisitor<MasterHouse> masterHouseRsqlVisitor = new CustomRsqlVisitor<MasterHouse>();

    public SearchResponseDto retreive(final String query, Integer pageNumber, Integer count) {
        Page<MasterHouse> result;
        if (query == null || query.length() == 0) {
            result = masterHouseRepository.findAll(PageRequest.of(PageableUtil.getPageNumber(pageNumber),
                    PageableUtil.getCount(count, DEFAULT_COUNT)));
        } else {
            Specification<MasterHouse> specification = rsqlParser.parse(query).accept(masterHouseRsqlVisitor);
            result = masterHouseRepository.findAll(specification, PageRequest.of(PageableUtil.getPageNumber(pageNumber),
                    PageableUtil.getCount(count, DEFAULT_COUNT)));
        }

        return SearchResponseDto.builder().result(result.getContent()).count(result.getNumberOfElements())
                .currentPage(result.getNumber() + 1).totalPages(result.getTotalPages()).build();
    }

}
