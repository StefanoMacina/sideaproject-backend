package com.sideagroup.academy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDto {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Double averageRating;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer numVotes;
}
