package com.open.library.utils.response;

import com.open.library.utils.request.PageDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
public class PageResponseDTO<T> {
    private int length;

    private int pageIndex;

    private int pageSize;

    private List<T> dataSource;


    public static PageResponseDTO getPage(PageDTO pageDTO, List<UserResponseDTO> results, long count) {
        return PageResponseDTO
                .builder()
                .length((int) count)
                .pageSize(pageDTO.getPageSize())
                .pageIndex(pageDTO.getPageIndex())
                .dataSource(Arrays.asList(results.toArray()))
                .build();
    }
}
