package com.behl.dolores.utility;

public class PageableUtil {

    public static int getCount(Integer count, Integer defaultCount) {
        if (count == null || count <= 0)
            return defaultCount;
        else
            return count;
    }

    public static int getPageNumber(Integer pageNumber) {
        if (pageNumber == null || pageNumber <= 0 || pageNumber == 1)
            return 0;
        else
            return pageNumber - 1;
    }

}