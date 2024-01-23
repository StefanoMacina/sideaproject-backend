package com.sideagroup.academy.controller.api;

import com.sideagroup.academy.dto.CelebrityDto;
import com.sideagroup.academy.dto.DefaultErrorDto;
import com.sideagroup.academy.dto.GetAllCelebritiesResponseDto;
import com.sideagroup.academy.exception.GenericServiceException;
import com.sideagroup.academy.service.CelebrityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/celebrities")
@Tag(name = "Celebrities", description = "Celebrities management APIs")
@CrossOrigin(origins = "*")
public class CelebrityController {
    @Autowired
    private CelebrityService celebrityServices;

    @Operation(summary = "Retrieves all celebrities without movies", description = "Retrieves all celebrities without movies in a paginated way")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "One or more parameters are invalid",
                    content = @Content(schema = @Schema(implementation = DefaultErrorDto.class)))
    })
    @GetMapping
    public GetAllCelebritiesResponseDto getAll(@RequestParam(name = "page", required = false, defaultValue = "0") @Parameter(description = "Page number", example = "0") int page,
                                               @RequestParam(name = "size", required = false, defaultValue = "20") @Parameter(description = "Page size", example = "30") int size,
                                               @RequestParam(name = "order_by", required = false, defaultValue = "id") @Parameter(description = "Field used for sorting", example = "id") String orderBy,
                                               @RequestParam(name = "name", required = false) @Parameter(description = "Searches for celebrities with name like this string", example = "samuel") String name) {
        try {
            return celebrityServices.getAll(page, size, orderBy, name);
        } catch (GenericServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Gets a celebrity with its movies by id", description = "Returns a celebrity with its movies as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully resolved"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found - The celebrity was not found",
                    content = @Content(schema = @Schema(implementation = DefaultErrorDto.class))
            )
    })
    @GetMapping("{id}")
    public CelebrityDto getById(@PathVariable("id")
                                @Parameter(description = "Celebrity id", example = "nm0012804") String id) {
        Optional<CelebrityDto> opt = celebrityServices.getById(id);
        if (opt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
        return opt.get();
    }


    @Operation(summary = "Creates a celebrity with its details", description = "Creates a celebrity with its details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request - A celebrity with this details already exists",
                    content = @Content(schema = @Schema(implementation = DefaultErrorDto.class))
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CelebrityDto create(@RequestBody CelebrityDto celebrity) {
        return celebrityServices.create(celebrity);
    }

    @Operation(summary = "Update a celebrity by Id", description = "Update a celebrity with its details by the specified Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found - A celebrity with this Id cannot be found",
                    content = @Content(schema = @Schema(implementation = DefaultErrorDto.class))
            )
    })
    @PutMapping("{id}")
    public CelebrityDto update(@PathVariable("id")
                               @Parameter(description = "Celebrity id", example = "nm0012804") String id,
                               @RequestBody CelebrityDto celebrity) {
        Optional<CelebrityDto> opt = celebrityServices.update(id, celebrity);
        if (opt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
        CelebrityDto myCelebrity = opt.get();
        myCelebrity.setName(celebrity.getName());
        myCelebrity.setBirthYear(celebrity.getBirthYear());
        myCelebrity.setDeathYear(celebrity.getDeathYear());

        return myCelebrity;
    }

    @Operation(summary = "Deletes a celebrity by Id", description = "Deletes a celebrity with the given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted")
    })
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id")
                           @Parameter(description = "Celebrity id", example = "nm0012804") String id) {
        celebrityServices.deleteById(id);
    }
}

