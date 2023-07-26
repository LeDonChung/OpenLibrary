package com.open.library.utils.request;

import lombok.Data;

import java.util.List;

@Data
public class PageDTO {
    private int length;

    private int pageIndex;

    private int pageSize;

    private int[] pageSizeOptions;
}
