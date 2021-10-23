package com.behl.dolores.rsql.constant;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.RSQLOperators;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RSQLSearchOperation {

    EQUAL(RSQLOperators.EQUAL), NOT_EQUAL(RSQLOperators.NOT_EQUAL), GREATER_THAN(RSQLOperators.GREATER_THAN),
    GREATER_THAN_OR_EQUAL(RSQLOperators.GREATER_THAN_OR_EQUAL), LESS_THAN(RSQLOperators.LESS_THAN),
    LESS_THAN_OR_EQUAL(RSQLOperators.LESS_THAN_OR_EQUAL), IN(RSQLOperators.IN), NOT_IN(RSQLOperators.NOT_IN),
    EQUAL_IGNORE_CASE(CustomOperator.EQUAL_IGNORE_CASE.getOperator()),
    NOT_EQUAL_IGNORE_CASE(CustomOperator.NOT_EQUAL_IGNORE_CASE.getOperator());

    private ComparisonOperator operator;

    public static RSQLSearchOperation getSimpleOperator(ComparisonOperator operator) {
        for (RSQLSearchOperation operation : values()) {
            if (operation.getOperator().getSymbol().contains(operator.getSymbol())) {
                return operation;
            }
        }
        return null;
    }

}