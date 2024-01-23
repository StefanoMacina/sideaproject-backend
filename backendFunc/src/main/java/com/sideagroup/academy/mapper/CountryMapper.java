package com.sideagroup.academy.mapper;

import com.sideagroup.academy.dto.CountryDto;
import com.sideagroup.academy.model.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {
    CountryDto toDto(Country entity){
        CountryDto dto = new CountryDto();
        dto.setLanguage(entity.getLanguage());
        dto.setTitle(entity.getTitle());
        dto.setRegion(entity.getRegion());
        return dto;
    }
}
