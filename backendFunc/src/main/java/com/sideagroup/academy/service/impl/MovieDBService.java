package com.sideagroup.academy.service.impl;

import com.sideagroup.academy.dto.GetAllMoviesResponseDto;
import com.sideagroup.academy.dto.MovieCelebrityDto;
import com.sideagroup.academy.dto.MovieDto;
import com.sideagroup.academy.exception.GenericServiceException;
import com.sideagroup.academy.mapper.MovieCelebrityMapper;
import com.sideagroup.academy.mapper.MovieMapper;
import com.sideagroup.academy.mapper.RatingMapper;
import com.sideagroup.academy.model.*;
import com.sideagroup.academy.repository.CelebrityRepository;
import com.sideagroup.academy.repository.MovieCelebrityRepository;
import com.sideagroup.academy.repository.MovieRepository;
import com.sideagroup.academy.repository.RatingRepository;
import com.sideagroup.academy.service.MovieService;
import com.sideagroup.academy.validator.MovieValidator;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Service("movieDbService")
public class MovieDBService implements MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieDBService.class);

    @Autowired
    private MovieRepository repo;

    @Autowired
    private MovieCelebrityRepository movieCelebrityRepository;

    @Autowired
    private CelebrityRepository celebrityRepo;

    @Autowired
    private MovieMapper mapper;

    @Autowired
    private MovieCelebrityMapper movieCelebrityMapper;

    @Autowired
    private RatingMapper ratingMapper;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private MovieValidator movieValidator;
    @Override
    public GetAllMoviesResponseDto getAll(int page, int size, String orderBy, String title) {
        logger.info("getAll called");
        movieValidator.validateQueryParams(orderBy);
        //boh
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        Page<Movie> movies = title == null ? repo.findAll(pageable) :
                repo.findByTitle("%" + title + "%", pageable);
        return mapper.toDto(movies, size);
    }

    //Consento di ordinare i risultati di ricerca solo in base a qualche campo
    private void validateInput(String orderBy) {
        if (!"id".equals(orderBy) && !"title".equals(orderBy) && !"year".equals(orderBy)) {
            throw new GenericServiceException("Invalid Sort field '" + orderBy + "'. Valid values are: [id, title, year]");
        }
    }

    @Override
    public MovieCelebrityDto associateCelebrity(String movieId, String celebrityId, MovieCelebrityDto body) {
        logger.info("Association called");
        Optional<Movie> movie = repo.findById(movieId);
        if (movie.isEmpty()) throw new GenericServiceException("Movie with id " + movieId + " does not exist!");
        Optional<Celebrity> celebrity = celebrityRepo.findById(celebrityId);
        if (celebrity.isEmpty())
            throw new GenericServiceException("Celebrity with id " + celebrityId + " does not exist!");

        MovieCelebrityKey key = new MovieCelebrityKey(celebrityId, movieId);
        Optional<MovieCelebrity> rel = movieCelebrityRepository.findById(key);
        if (rel.isPresent())
            return movieCelebrityMapper.toDto(rel.get());

        MovieCelebrity entity = new MovieCelebrity(key);
        entity.setCelebrity(celebrity.get());
        entity.setMovie(movie.get());
        entity.setCategory(body.getCategory());
        entity.setCharacters(body.getCharacters());
        entity = movieCelebrityRepository.save(entity);
        return movieCelebrityMapper.toDto(entity);
    }

    @Override
    public boolean removeAssociation(String movieId, String celebrityId){
        logger.info("Remove Association called");
        MovieCelebrityKey key = new MovieCelebrityKey(celebrityId, movieId);
        Optional<MovieCelebrity> opt = movieCelebrityRepository.findById(key);
        if(opt.isEmpty())
            return false;
        movieCelebrityRepository.deleteById(key);
        return true;
    }

    @Override
    public Optional<MovieDto> getById(String id) {
        logger.info("getById called");
        //ricerca per id tramite le entità
        Optional<Movie> result = repo.findById(id);
        if (!result.isEmpty()) {
            //se c'è riscontro, mappa l'entità a dto e restituisci
            MovieDto dto = mapper.toDto(result.get(), true, true);
            return Optional.of(dto);
        }
        return Optional.empty();
    }


    @Override
    @Transactional
    public MovieDto create(MovieDto movie) {
        logger.info("create called");
        Optional<Movie> opt = repo.findById(movie.getId());
        if (!opt.isEmpty()) throw new GenericServiceException("Movie with id " + movie.getId() + " already exists");
        //trasformo il dto in entità, lo salvo con save, cioè ci faccio una insert
        Movie entity = repo.save(mapper.toEntity(movie));

        Rating ratingEntity = ratingMapper.toEntity(movie.getRating());
        ratingEntity.setMovie(entity);
        ratingEntity = ratingRepository.save(ratingEntity);
        entity.setRating(ratingEntity);
        return mapper.toDto(entity, false, false);
    }

    @Override
    public Optional<MovieDto> update(String id, MovieDto movie) {
        logger.info("update called");
        //cerca tra i film per id
        Optional<Movie> opt = repo.findById(id);
        if (opt.isEmpty()) return Optional.empty();
        //prendi l'entità, aggiorna l'entità usando i dati del dto, salva(insert), restituisci il dto
        Movie entity = opt.get();
        mapper.updateFromDto(entity, movie);
        entity = repo.save(entity);
        return Optional.of(mapper.toDto(entity, false, false));
    }

    @Override
    @Transactional
    public boolean deleteById(String id) {
        logger.info("deleteById called");
        Optional<Movie> movie = repo.findById(id);
        if(movie.isEmpty())
            return false;
        ratingRepository.deleteById(movie.get().getRating().getId());
        repo.deleteById(id);
        return true;
    }
}
