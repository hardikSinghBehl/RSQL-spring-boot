package com.behl.dolores.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.behl.dolores.dto.RsqlSearchRequestDto;
import com.behl.dolores.dto.SearchResponseDto;
import com.behl.dolores.entity.Wizard;
import com.behl.dolores.repository.WizardRepository;
import com.behl.dolores.rsql.CustomRsqlVisitor;
import com.behl.dolores.utility.PageableUtil;
import com.behl.dolores.utility.ResponseBuilder;
import com.behl.dolores.utility.SortingUtil;

import cz.jirutka.rsql.parser.RSQLParser;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WizardService {

    private static final Integer DEFAULT_COUNT = 10000;

    private final WizardRepository wizardRepository;
    private final RSQLParser rsqlParser;
    private final CustomRsqlVisitor<Wizard> wizardRsqlVisitor = new CustomRsqlVisitor<Wizard>();

    public SearchResponseDto retreive(final RsqlSearchRequestDto rsqlSearchRequestDto) {
        Page<Wizard> result;
        final String query = rsqlSearchRequestDto.getQuery();
        final Integer count = rsqlSearchRequestDto.getCount();
        final Integer page = rsqlSearchRequestDto.getPage();
        final String sort = rsqlSearchRequestDto.getSort();

        if (query == null || query.length() == 0) {
            result = wizardRepository.findAll(PageRequest.of(PageableUtil.getPageNumber(null, count),
                    PageableUtil.getCount(null, DEFAULT_COUNT), SortingUtil.build(sort)));
        } else {
            Specification<Wizard> specification = rsqlParser.parse(query).accept(wizardRsqlVisitor);
            result = wizardRepository.findAll(specification, PageRequest.of(PageableUtil.getPageNumber(page, count),
                    PageableUtil.getCount(count, DEFAULT_COUNT), SortingUtil.build(sort)));
        }
        return ResponseBuilder.build(result);
    }

}
