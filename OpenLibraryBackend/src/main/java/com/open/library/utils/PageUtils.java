package com.open.library.utils;

import com.open.library.utils.request.PageDTO;
import com.open.library.utils.request.SorterDTO;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
public class PageUtils {
    private int length;

    private int pageIndex;

    private int pageSize;

    private List<Object> dataSource;

    private SorterDTO sorter;

    public static PageUtils getPage(PageDTO pageDTO, List<Object> results, int count) {
        return PageUtils
                .builder()
                .length(count)
                .sorter(pageDTO.getSorter())
                .pageSize(pageDTO.getPageSize())
                .pageIndex(pageDTO.getPageIndex())
                .dataSource(Arrays.asList(results.toArray()))
                .build();
    }
}
