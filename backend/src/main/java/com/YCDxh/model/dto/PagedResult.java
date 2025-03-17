package com.YCDxh.model.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PagedResult<T> {
    private int currentPage;
    private int totalPages;
    private long totalElements;
    private List<T> content;

    public PagedResult(Page<T> page) {
        this.currentPage = page.getNumber() + 1; // 页码从1开始
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.content = page.getContent();
    }
}
