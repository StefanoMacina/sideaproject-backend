package com.sideagroup.academy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CelebrityDto {
    @Schema(description = "Celebrity Id", example = "nm0120804", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;
    @Schema(description = "Celebrity name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @Schema(description = "Birth year", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer birthYear;
    @Schema(description = "Death year", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer deathYear;
    @Schema(description = "Movies' list", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<MovieCelebrityDto> movies;
    public CelebrityDto() { movies = new ArrayList<>(); }
}
