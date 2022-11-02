package com.amaris.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import java.util.List;
@Data
@NoArgsConstructor
public class PageResponse<T> {
    private long pageNo;
    private long pageSize;
    private long totalSize;
    private long totalPage;
    private List<T> list;
    public PageResponse(List<T> data,Pageable page) {
        pageNo = page.getPageNumber();
        pageSize = page.getPageSize();
        list = data;
    }

    public PageResponse(long pageNo, long pageSize, long totalSize, long totalPage, List<T> list) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
        this.totalPage = totalPage;
        this.list = list;
    }
}
