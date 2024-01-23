package com.sideagroup.academy.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GetAllCelebritiesResponseDto {
    private PaginationDto pagination;
    private List<CelebrityDto> celebrities;

    public GetAllCelebritiesResponseDto(){
        pagination = new PaginationDto();
        celebrities = new ArrayList<>();
    }
}
