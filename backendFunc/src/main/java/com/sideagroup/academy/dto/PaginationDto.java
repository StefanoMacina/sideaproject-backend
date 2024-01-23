package com.sideagroup.academy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationDto {
    private int currentPage;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
