package com.behl.dolores.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.behl.dolores.dto.SearchResponseDto;
import com.behl.dolores.entity.Wizard;
import com.behl.dolores.repository.WizardRepository;
import com.behl.dolores.rsql.CustomRsqlVisitor;
import com.behl.dolores.utility.PageableUtil;

import cz.jirutka.rsql.parser.RSQLParser;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WizardService {

    private static final Integer DEFAULT_COUNT = 10000;

    private final WizardRepository wizardRepository;
    private final RSQLParser rsqlParser;
    private final CustomRsqlVisitor<Wizard> wizardRsqlVisitor = new CustomRsqlVisitor<Wizard>();

    public SearchResponseDto retreive(final String query, Integer pageNumber, Integer count) {
        Page<Wizard> result;
        if (query == null || query.length() == 0) {
            result = wizardRepository.findAll(PageRequest.of(PageableUtil.getPageNumber(pageNumber),
                    PageableUtil.getCount(count, DEFAULT_COUNT)));
        } else {
            Specification<Wizard> specification = rsqlParser.parse(query).accept(wizardRsqlVisitor);
            result = wizardRepository.findAll(specification, PageRequest.of(PageableUtil.getPageNumber(pageNumber),
                    PageableUtil.getCount(count, DEFAULT_COUNT)));
        }

        return SearchResponseDto.builder().result(result.getContent()).count(result.getNumberOfElements())
                .currentPage(result.getNumber() + 1).totalPages(result.getTotalPages()).build();
    }

}
