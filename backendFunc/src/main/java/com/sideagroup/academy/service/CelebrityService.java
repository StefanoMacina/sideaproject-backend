package com.sideagroup.academy.service;

import com.sideagroup.academy.dto.CelebrityDto;
import com.sideagroup.academy.dto.GetAllCelebritiesResponseDto;

import java.util.Optional;


public interface CelebrityService {

    GetAllCelebritiesResponseDto getAll(int page, int size, String orderBy, String name);

    Optional<CelebrityDto> getById(String id);

    CelebrityDto create(CelebrityDto celebrity);

    Optional<CelebrityDto> update(String id, CelebrityDto celebrity);

    boolean deleteById(String id);
}
