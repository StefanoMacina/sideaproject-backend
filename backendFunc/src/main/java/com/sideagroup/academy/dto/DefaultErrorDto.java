package com.sideagroup.academy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DefaultErrorDto {
    private Date timestamp;
    @Schema(description = "HTTP Status code", example = "404")
    private int status;
    @Schema(description = "Error detail", example = "Item not found")
    private String error;
    @Schema(description = "HTTP Status message", example = "Not Found")
    private String message;
    @Schema(description = "Requested path", example = "/api/v1/movies/tt01207370")
    private String path;
}
