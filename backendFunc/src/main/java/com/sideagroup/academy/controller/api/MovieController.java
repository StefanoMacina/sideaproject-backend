package com.sideagroup.academy.controller.api;

import com.sideagroup.academy.dto.DefaultErrorDto;
import com.sideagroup.academy.dto.GetAllMoviesResponseDto;
import com.sideagroup.academy.dto.MovieCelebrityDto;
import com.sideagroup.academy.dto.MovieDto;
import com.sideagroup.academy.exception.GenericServiceException;
import com.sideagroup.academy.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
@Tag(name = "Movies", description = "Movie management APIs")
@CrossOrigin(origins = "*")
public class MovieController {

    @Autowired
    private MovieService movieServices;

    @Operation(summary = "Retrieves all movies without cast", description = "Retrieves all movies without cast in a paginated way")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "One or more parameters are invalid",
                    content = @Content(schema = @Schema(implementation = DefaultErrorDto.class)))
    })
    @GetMapping
    public GetAllMoviesResponseDto getAll(@RequestParam(name = "page", required = false, defaultValue = "0") @Parameter(description = "Page number", example = "0") int page,
                                          @RequestParam(name = "size", required = false, defaultValue = "20") @Parameter(description = "Page size", example = "30") int size,
                                          @RequestParam(name = "order_by", required = false, defaultValue = "id") @Parameter(description = "Field used for sorting", example = "id") String orderBy,
                                          @RequestParam(name = "title", required = false) @Parameter(description = "Searches for movies with title like this string", example = "star") String title) {
        try {
            return movieServices.getAll(page, size, orderBy, title);
        } catch (GenericServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @Operation(summary = "Gets a movie with its cast by id", description = "Returns a movie with its cast as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully resolved"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found - The movie was not found",
                    content = @Content(schema = @Schema(implementation = DefaultErrorDto.class))
            )
    })
    @GetMapping("{id}")
    public MovieDto getById(@PathVariable("id")
                            @Parameter(description = "Movie id", example = "tt0012804") String id) {
        Optional<MovieDto> opt = movieServices.getById(id);
        if (opt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
        return opt.get();
    }

    @Operation(summary = "Creates a movie", description = "Creates a movie with all its details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request - A movie with same details already exists",
                    content = @Content(schema = @Schema(implementation = DefaultErrorDto.class))
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDto create(@RequestBody MovieDto movie) {
        return movieServices.create(movie);
    }


    @Operation(summary = "Updates a movie", description = "Updates the details of a movie with the given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found - Movie with specified id does not exist",
                    content = @Content(schema = @Schema(implementation = DefaultErrorDto.class))
            )
    })
    @PutMapping("{id}")
    public MovieDto update(@PathVariable("id")
                           @Parameter(description = "Movie id", example = "tt0012804") String id,
                           @RequestBody MovieDto movie) {
        Optional<MovieDto> opt = movieServices.update(id, movie);
        if (opt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
        MovieDto myMovie = opt.get();
        myMovie.setTitle(movie.getTitle());
        myMovie.setYear(movie.getYear());
        myMovie.setRunningTime(movie.getRunningTime());
        myMovie.setGenres(movie.getGenres());

        return myMovie;
    }


    @Operation(summary = "Deletes a movie", description = "Deletes a movie with the given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted")
    })
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id")
                           @Parameter(description = "Movie id", example = "tt0012804") String id) {
        movieServices.deleteById(id);
    }


    @Operation(summary = "Creates an association", description = "Associates a movie with a celebrity by their IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found - Movie or celebrity with specified id does not exist",
                    content = @Content(schema = @Schema(implementation = DefaultErrorDto.class))
            )
    })
    @PutMapping("{movieId}/celebrities/{celebrityId}")
    public MovieCelebrityDto associationCelebrity(@PathVariable("movieId")
                                                  @Parameter(description = "Movie id", example = "tt0012804") String movieId,
                                                  @PathVariable("celebrityId")
                                                  @Parameter(description = "Celebrity id", example = "nm0012804") String celebrityId,
                                                  @RequestBody MovieCelebrityDto body) {
        try {
            return movieServices.associateCelebrity(movieId, celebrityId, body);
        } catch (GenericServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @Operation(summary = "Deletes an association", description = "Removes an association between a movie and its celebrity by their IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted")
    })
    @DeleteMapping("{movieId}/celebrities/{celebrityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAssociation(@PathVariable("movieId")
                                  @Parameter(description = "Movie id", example = "tt0012804") String movieId,
                                  @PathVariable("celebrityId")
                                  @Parameter(description = "Celebrity id", example = "nm0012804") String celebrityId) {
        movieServices.removeAssociation(movieId, celebrityId);
    }
}


