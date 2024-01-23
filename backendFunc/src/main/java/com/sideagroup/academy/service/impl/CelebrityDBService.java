package com.sideagroup.academy.service.impl;

import com.sideagroup.academy.dto.CelebrityDto;
import com.sideagroup.academy.dto.GetAllCelebritiesResponseDto;
import com.sideagroup.academy.exception.GenericServiceException;
import com.sideagroup.academy.mapper.CelebrityMapper;
import com.sideagroup.academy.mapper.MovieCelebrityMapper;
import com.sideagroup.academy.model.Celebrity;
import com.sideagroup.academy.repository.CelebrityRepository;
import com.sideagroup.academy.repository.MovieCelebrityRepository;
import com.sideagroup.academy.service.CelebrityService;
import com.sideagroup.academy.validator.CelebrityValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("celebrityDbService")
public class CelebrityDBService implements CelebrityService {

    private static final Logger logger = LoggerFactory.getLogger(CelebrityDBService.class);

    @Autowired
    private CelebrityRepository repo;

    @Autowired
    private MovieCelebrityRepository movieCelebrityRepository;

    @Autowired
    private CelebrityMapper mapper;

    @Autowired
    private MovieCelebrityMapper movieCelebrityMapper;

    @Autowired
    private CelebrityValidator celebrityValidator;

    @Override
    public GetAllCelebritiesResponseDto getAll(int page, int size, String orderBy, String name) {
        logger.info("getAll called");
        celebrityValidator.validateQueryParams(orderBy);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        Page<Celebrity> celebrities = name == null ? repo.findAll(pageable) :
        repo.findByPrimaryNameContainingIgnoreCase(name, pageable);
        return mapper.toDto(celebrities, size);
    }

    @Override
    public Optional<CelebrityDto> getById(String id) {
        logger.info("getById called");
        Optional<Celebrity> result = repo.findById(id);
        if(result.isPresent()) {
            CelebrityDto dto = mapper.toDto(result.get(), true);
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Override
    public CelebrityDto create(CelebrityDto celebrity) {
        logger.info("create called");
        Optional<Celebrity> opt = repo.findById(celebrity.getId());
        if(opt.isPresent())
            throw new GenericServiceException("Celebrity with id " + celebrity.getId() + " already exists!");
        Celebrity entity = repo.save(mapper.toEntity(celebrity));
        return mapper.toDto(entity, false);
    }

    @Override
    public Optional<CelebrityDto> update(String id, CelebrityDto celebrity) {
        logger.info("update called");
        Optional<Celebrity> opt = repo.findById(id);
        if(opt.isEmpty())
            return Optional.empty();

        Celebrity entity = opt.get();
        mapper.updateFromDto(entity, celebrity);
        entity = repo.save(entity);
        return Optional.of(mapper.toDto(entity, false));
    }

    @Override
    public boolean deleteById(String id) {
        logger.info("deleteById called");
        repo.deleteById(id);
        return true;
    }
}
