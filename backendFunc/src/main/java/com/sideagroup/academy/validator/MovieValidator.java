package com.sideagroup.academy.validator;

import com.sideagroup.academy.dto.MovieDto;
import org.springframework.stereotype.Component;
import com.sideagroup.academy.exception.GenericServiceException;

@Component
public class MovieValidator {
    public void validateQueryParams(String orderBy) {
        if (!"id".equals(orderBy) && !"title".equals(orderBy) && !"year".equals(orderBy)) {
            throw new GenericServiceException("Invalid Sort field '" + orderBy + "'. Valid values are: [id, title, year]");
        }
    }

    public void validateMovieRequest(MovieDto movie) {
        if (movie.getRating() == null) throw new GenericServiceException("Missing field 'rating'");
    }
}
