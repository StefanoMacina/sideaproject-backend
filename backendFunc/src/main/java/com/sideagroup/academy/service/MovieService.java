package com.sideagroup.academy.service;

import com.sideagroup.academy.dto.GetAllMoviesResponseDto;
import com.sideagroup.academy.dto.MovieCelebrityDto;
import com.sideagroup.academy.dto.MovieDto;
import com.sideagroup.academy.model.MovieCelebrityKey;

import java.util.ArrayList;
import java.util.Optional;

public interface MovieService {
    GetAllMoviesResponseDto getAll(int page, int size, String orderBy, String title);

    Optional<MovieDto> getById(String id);

    MovieDto create(MovieDto movie);

    Optional<MovieDto> update(String id, MovieDto movie);

    boolean deleteById(String id);

    MovieCelebrityDto associateCelebrity(String movieId, String celebrityId, MovieCelebrityDto body);

    boolean removeAssociation(String movieId, String celebrityId);
}
