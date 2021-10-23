package com.behl.dolores.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.behl.dolores.dto.RsqlSearchRequestDto;
import com.behl.dolores.dto.SearchResponseDto;
import com.behl.dolores.entity.MasterHouse;
import com.behl.dolores.repository.MasterHouseRepository;
import com.behl.dolores.rsql.CustomRsqlVisitor;
import com.behl.dolores.utility.PageableUtil;
import com.behl.dolores.utility.ResponseBuilder;
import com.behl.dolores.utility.SortingUtil;

import cz.jirutka.rsql.parser.RSQLParser;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MasterHouseService {

    private static final Integer DEFAULT_COUNT = 4;

    private final MasterHouseRepository masterHouseRepository;
    private final RSQLParser rsqlParser;
    private final CustomRsqlVisitor<MasterHouse> masterHouseRsqlVisitor = new CustomRsqlVisitor<MasterHouse>();

    public SearchResponseDto retreive(final RsqlSearchRequestDto rsqlSearchRequestDto) {
        Page<MasterHouse> result;
        final String query = rsqlSearchRequestDto.getQuery();
        final Integer count = rsqlSearchRequestDto.getCount();
        final Integer page = rsqlSearchRequestDto.getPage();
        final String sort = rsqlSearchRequestDto.getSort();

        if (query == null || query.length() == 0) {
            result = masterHouseRepository.findAll(PageRequest.of(PageableUtil.getPageNumber(page),
                    PageableUtil.getCount(count, DEFAULT_COUNT), SortingUtil.build(sort)));
        } else {
            Specification<MasterHouse> specification = rsqlParser.parse(query).accept(masterHouseRsqlVisitor);
            result = masterHouseRepository.findAll(specification, PageRequest.of(PageableUtil.getPageNumber(page),
                    PageableUtil.getCount(count, DEFAULT_COUNT), SortingUtil.build(sort)));
        }
        return ResponseBuilder.build(result);
    }

}
