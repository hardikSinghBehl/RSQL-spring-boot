package com.behl.dolores.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "com.behl.dolores")
public class PaginationProperties {

    private Pagination pagination = new Pagination();

    @Data
    public class Pagination {
        private Integer defaultCount;
    }

}
