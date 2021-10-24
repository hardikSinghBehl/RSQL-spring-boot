package com.behl.dolores.rsql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.behl.dolores.entity.constant.Gender;
import com.behl.dolores.entity.constant.Species;
import com.behl.dolores.exception.PossibleSqlInjectionAttackException;
import com.behl.dolores.rsql.constant.RSQLSearchOperation;
import com.github.rkpunjal.sqlsafe.SqlSafeUtil;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RSQLSpecification<T> implements Specification<T> {

    private static final String NULL = "null";
    private static final long serialVersionUID = 5546046598898481992L;
    private String property;
    private ComparisonOperator operator;
    private List<String> arguments;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Object> args = castArguments(root);
        Object argument = args.get(0);
        switch (RSQLSearchOperation.getSimpleOperator(operator)) {
        case EQUAL:
            if (argument instanceof LocalDateTime) {
                return builder.equal(root.<LocalDateTime>get(property), (LocalDateTime) argument);
            } else if (argument instanceof LocalDate) {
                return builder.equal(root.<LocalDate>get(property), (LocalDate) argument);
            } else if (argument instanceof String) {
                return builder.like(getAbsolutePath(root, property), argument.toString().replace('*', '%'));
            } else if (argument == null) {
                return builder.isNull(getAbsolutePath(root, property));
            } else {
                return builder.equal(getAbsolutePath(root, property), argument);
            }
        case NOT_EQUAL:
            if (argument instanceof LocalDateTime) {
                return builder.notEqual(root.<LocalDateTime>get(property), (LocalDateTime) argument);
            } else if (argument instanceof LocalDate) {
                return builder.notEqual(root.<LocalDate>get(property), (LocalDate) argument);
            } else if (argument instanceof String) {
                return builder.notLike(root.<String>get(property), argument.toString().replace('*', '%'));
            } else if (argument == null) {
                return builder.isNotNull(getAbsolutePath(root, property));
            } else {
                return builder.notEqual(getAbsolutePath(root, property), argument);
            }
        case EQUAL_IGNORE_CASE:
            return builder.createQuery().where(builder.or(builder.like(builder.lower(root.<String>get(property)),
                    "%" + argument.toString().toLowerCase() + "%"))).getRestriction();
        case NOT_EQUAL_IGNORE_CASE:
            return builder.createQuery().where(builder.or(builder.like(builder.lower(root.<String>get(property)),
                    "%" + argument.toString().toLowerCase() + "%"))).getRestriction().not();
        case GREATER_THAN:
            if (argument instanceof LocalDateTime)
                return builder.greaterThan(root.<LocalDateTime>get(property), (LocalDateTime) argument);
            if (argument instanceof LocalDate)
                return builder.greaterThan(root.<LocalDate>get(property), ((LocalDate) argument));
            if (argument instanceof Double)
                return builder.createQuery()
                        .where(builder.greaterThan(getAbsolutePath(root, property), argument.toString()))
                        .getRestriction();
            return builder.greaterThan(root.<String>get(property), argument.toString());
        case GREATER_THAN_OR_EQUAL:
            if (argument instanceof LocalDateTime)
                return builder.greaterThanOrEqualTo(root.<LocalDateTime>get(property), (LocalDateTime) argument);
            if (argument instanceof LocalDate)
                return builder.greaterThanOrEqualTo(root.<LocalDate>get(property), ((LocalDate) argument));
            if (argument instanceof Double)
                return builder.createQuery()
                        .where(builder.greaterThanOrEqualTo(getAbsolutePath(root, property), argument.toString()))
                        .getRestriction();
            return builder.greaterThanOrEqualTo(root.<String>get(property), argument.toString());
        case LESS_THAN:
            if (argument instanceof LocalDateTime)
                return builder.lessThan(root.<LocalDateTime>get(property), (LocalDateTime) argument);
            if (argument instanceof LocalDate)
                return builder.lessThan(root.<LocalDate>get(property), ((LocalDate) argument));
            if (argument instanceof Double)
                return builder.createQuery()
                        .where(builder.lessThan(getAbsolutePath(root, property), argument.toString())).getRestriction();
            return builder.lessThan(root.<String>get(property), argument.toString());
        case LESS_THAN_OR_EQUAL:
            if (argument instanceof LocalDateTime)
                return builder.lessThanOrEqualTo(root.<LocalDateTime>get(property), (LocalDateTime) argument);
            if (argument instanceof LocalDate)
                return builder.lessThanOrEqualTo(root.<LocalDate>get(property), ((LocalDate) argument));
            if (argument instanceof Double)
                return builder.createQuery()
                        .where(builder.lessThanOrEqualTo(getAbsolutePath(root, property), argument.toString()))
                        .getRestriction();
            return builder.lessThanOrEqualTo(root.<String>get(property), argument.toString());
        case IN:
            return getAbsolutePath(root, property).in(args);
        case NOT_IN:
            return builder.not(getAbsolutePath(root, property).in(args));
        default:
            return null;
        }
    }

    private Path<String> getAbsolutePath(final Path<?> path, final String property) {
        if (property.contains(".")) {
            return getAbsolutePath(path.get(property.substring(0, property.indexOf("."))),
                    property.substring(property.indexOf(".") + 1));
        }
        return path.get(property);
    }

    private List<Object> castArguments(final Root<T> root) {
        Class<? extends Object> type = getAbsolutePath(root, property).getJavaType();
        return arguments.stream().map(argument -> {
            if (!SqlSafeUtil.isSqlInjectionSafe(argument))
                throw new PossibleSqlInjectionAttackException();
            if (type.isEnum()) {
                return argument.equalsIgnoreCase(NULL) ? null : retreiveEnumClass(type, argument);
            } else if (type.equals(UUID.class)) {
                return argument.equalsIgnoreCase(NULL) ? null : UUID.fromString(argument);
            } else if (type.equals(LocalDate.class)) {
                return argument.equalsIgnoreCase(NULL) ? null : LocalDate.parse(argument);
            } else if (type.equals(LocalDateTime.class)) {
                return argument.equalsIgnoreCase(NULL) ? null : LocalDateTime.parse(argument);
            } else if (type.equals(Integer.class)) {
                return argument.equalsIgnoreCase(NULL) ? null : Integer.parseInt(argument);
            } else if (type.equals(Long.class)) {
                return argument.equalsIgnoreCase(NULL) ? null : Long.parseLong(argument);
            } else if (type.equals(Double.class)) {
                return argument.equalsIgnoreCase(NULL) ? null : Double.valueOf(argument);
            } else if (type.equals(Boolean.class)) {
                if (argument.equals("1"))
                    return true;
                if (argument.equals("0"))
                    return false;
                return Boolean.valueOf(argument);
            } else {
                return argument;
            }
        }).collect(Collectors.toList());
    }

    private Object retreiveEnumClass(final Class<? extends Object> type, final String argument) {
        if (type.getSimpleName().equalsIgnoreCase(Gender.class.getSimpleName()))
            return Enum.valueOf(Gender.class, argument.toUpperCase());
        else if (type.getSimpleName().equalsIgnoreCase(Species.class.getSimpleName()))
            return Enum.valueOf(Species.class, argument.toUpperCase());
        else
            return null;
    }

}