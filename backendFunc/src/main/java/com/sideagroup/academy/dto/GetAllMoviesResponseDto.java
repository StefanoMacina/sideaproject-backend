package com.sideagroup.academy.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GetAllMoviesResponseDto {
    private PaginationDto pagination;
    private List<MovieDto> movies;

    public GetAllMoviesResponseDto(){
        pagination = new PaginationDto();
        movies = new ArrayList<>();
    }
}
