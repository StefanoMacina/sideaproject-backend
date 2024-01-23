package com.sideagroup.academy.dto;

import com.sideagroup.academy.model.MovieCelebrity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MovieDto {
    @Schema(description = "Movie Id", example = "tt0120804", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;
    @Schema(description = "Movie title", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    @Schema(description = "Movie year", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer year;
    @Schema(description = "Running times in minutes", example = "104", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer runningTime;
    @Schema(description = "Comma separated genres of a movie", example = "Action, adventure", requiredMode = Schema.RequiredMode.REQUIRED)
    private String genres;
    @Schema(description = "Movie cast", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<MovieCelebrityDto> cast;
    @Schema(description = "Movie rating, required only for POST", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private RatingDto rating;
    @Schema(description = "Movie countries", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CountryDto> country;
    public MovieDto() {
        cast = new ArrayList<>();
        country = new ArrayList<>();
    }
}

