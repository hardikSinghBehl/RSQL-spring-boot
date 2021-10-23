package com.behl.dolores.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.behl.dolores.dto.SearchResponseDto;
import com.behl.dolores.entity.Wand;
import com.behl.dolores.repository.WandRepository;
import com.behl.dolores.rsql.CustomRsqlVisitor;
import com.behl.dolores.utility.PageableUtil;

import cz.jirutka.rsql.parser.RSQLParser;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WandService {

    private static final Integer DEFAULT_COUNT = 2000;

    private final WandRepository wandRepository;
    private final RSQLParser rsqlParser;
    private final CustomRsqlVisitor<Wand> wandRsqlVisitor = new CustomRsqlVisitor<Wand>();

    public SearchResponseDto retreive(final String query, Integer pageNumber, Integer count) {
        Page<Wand> result;
        if (query == null || query.length() == 0) {
            result = wandRepository.findAll(PageRequest.of(PageableUtil.getPageNumber(pageNumber),
                    PageableUtil.getCount(count, DEFAULT_COUNT)));
        } else {
            Specification<Wand> specification = rsqlParser.parse(query).accept(wandRsqlVisitor);
            result = wandRepository.findAll(specification, PageRequest.of(PageableUtil.getPageNumber(pageNumber),
                    PageableUtil.getCount(count, DEFAULT_COUNT)));
        }

        return SearchResponseDto.builder().result(result.getContent()).count(result.getNumberOfElements())
                .currentPage(result.getNumber() + 1).totalPages(result.getTotalPages()).build();
    }

}