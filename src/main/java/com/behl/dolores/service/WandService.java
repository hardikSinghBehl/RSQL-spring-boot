package com.behl.dolores.service;

import static com.behl.dolores.utility.CommonUtil.isEmpty;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.behl.dolores.dto.RsqlSearchRequestDto;
import com.behl.dolores.dto.SearchResponseDto;
import com.behl.dolores.entity.Wand;
import com.behl.dolores.properties.PaginationProperties;
import com.behl.dolores.repository.WandRepository;
import com.behl.dolores.rsql.CustomRsqlVisitor;
import com.behl.dolores.utility.PageableUtil;
import com.behl.dolores.utility.ResponseBuilder;
import com.behl.dolores.utility.SortingUtil;

import cz.jirutka.rsql.parser.RSQLParser;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@EnableConfigurationProperties(value = PaginationProperties.class)
public class WandService {

    private final PaginationProperties paginationProperties;
    private final WandRepository wandRepository;
    private final RSQLParser rsqlParser;
    private final CustomRsqlVisitor<Wand> wandRsqlVisitor = new CustomRsqlVisitor<Wand>();

    public SearchResponseDto retreive(final RsqlSearchRequestDto rsqlSearchRequestDto) {
        Page<Wand> result;
        final String query = rsqlSearchRequestDto.getQuery();
        final Integer count = rsqlSearchRequestDto.getCount();
        final Integer page = rsqlSearchRequestDto.getPage();
        final String sort = rsqlSearchRequestDto.getSort();
        final Integer DEFAULT_COUNT = paginationProperties.getPagination().getDefaultCount();

        if (isEmpty(query)) {
            result = wandRepository.findAll(PageRequest.of(PageableUtil.getPageNumber(null, count),
                    PageableUtil.getCount(null, DEFAULT_COUNT), SortingUtil.build(sort)));
        } else {
            Specification<Wand> specification = rsqlParser.parse(query).accept(wandRsqlVisitor);
            result = wandRepository.findAll(specification, PageRequest.of(PageableUtil.getPageNumber(page, count),
                    PageableUtil.getCount(count, DEFAULT_COUNT), SortingUtil.build(sort)));

            if (PageableUtil.exceedsTotalPageCount(result))
                result = wandRepository.findAll(specification,
                        PageRequest.of(PageableUtil.getPageNumber(result.getTotalPages(), count),
                                PageableUtil.getCount(count, DEFAULT_COUNT), SortingUtil.build(sort)));
        }
        return ResponseBuilder.build(result);
    }

}