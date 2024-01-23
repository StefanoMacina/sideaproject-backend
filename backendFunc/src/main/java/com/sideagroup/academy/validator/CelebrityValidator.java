package com.sideagroup.academy.validator;

import com.sideagroup.academy.exception.GenericServiceException;
import org.springframework.stereotype.Component;

@Component
public class CelebrityValidator {
    public void validateQueryParams(String orderBy) {
        if (!"id".equals(orderBy) && !"title".equals(orderBy) && !"year".equals(orderBy)) {
            throw new GenericServiceException("Invalid Sort field '" + orderBy + "'. Valid values are: [id, title, year]");
        }
    }
}
