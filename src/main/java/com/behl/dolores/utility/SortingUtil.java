package com.behl.dolores.utility;

import org.springframework.data.domain.Sort;

public class SortingUtil {

    public static Sort build(final String sortArgument) {
        if (sortArgument == null || sortArgument.length() == 0)
            return Sort.by("id");
        else
            return null;
    }

}
