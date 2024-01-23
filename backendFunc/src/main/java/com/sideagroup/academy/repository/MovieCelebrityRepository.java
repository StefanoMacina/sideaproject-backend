package com.sideagroup.academy.repository;

import com.sideagroup.academy.model.MovieCelebrity;
import com.sideagroup.academy.model.MovieCelebrityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieCelebrityRepository extends JpaRepository<MovieCelebrity, MovieCelebrityKey> {
}
