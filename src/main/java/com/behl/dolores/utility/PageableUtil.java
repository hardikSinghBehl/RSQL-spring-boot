package com.behl.dolores.utility;

import org.springframework.data.domain.Page;

public class PageableUtil {

    public static int getCount(Integer count, Integer defaultCount) {
        if (count == null || count <= 0)
            return defaultCount;
        else
            return count;
    }

    public static int getPageNumber(Integer pageNumber, Integer count) {
        if (pageNumber == null || pageNumber <= 0 || pageNumber == 1 || count == null || count <= 0)
            return 0;
        else
            return pageNumber - 1;
    }

    public static boolean exceedsTotalPageCount(final Page<?> result) {
        return result.getTotalPages() < result.getNumber() + 1;
    }

}
