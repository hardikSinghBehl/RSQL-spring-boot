package com.behl.dolores.repository.rsql.constant;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomOperator {

    EQUAL_IGNORE_CASE(new ComparisonOperator("=eic=", true)),
    NOT_EQUAL_IGNORE_CASE(new ComparisonOperator("=neic=", true));

    private ComparisonOperator operator;

}
