package com.behl.dolores.utility;

import org.springframework.data.domain.Sort;

public class SortingUtil {

    public static Sort build(String sortArguments) {
        Sort sort = Sort.unsorted();
        if (sortArguments == null || sortArguments.length() == 0)
            return sort;
        else {
            sortArguments = sortArguments.replaceAll("'", "").replaceAll("\"", "");
            for (String sortArgument : sortArguments.split("(?=\\@|\\$)")) {
                if (sortArgument.charAt(0) == '$')
                    sort = sort.and(Sort.by(sortArgument.substring(1)).descending());
                else if (sortArgument.charAt(0) == '@')
                    sort = sort.and(Sort.by(sortArgument.substring(1)).ascending());
                else
                    throw new RuntimeException();
            }
            return sort;
        }
    }

}
