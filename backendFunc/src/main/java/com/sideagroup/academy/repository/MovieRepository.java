package com.sideagroup.academy.repository;

import com.sideagroup.academy.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    @Query("select m from Movie m where upper(m.title) like upper(:title)")
    Page<Movie> findByTitle(@Param("title") String title, Pageable pageable);
}
