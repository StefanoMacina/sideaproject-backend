package com.sideagroup.academy.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

@Getter
@Entity
public class Movie {
    @Id
    @Column(length = 200)
    private String id;
    @Column(length = 1000, nullable = false)
    private String title;
    @Column(name="start_year")
    private Integer year;
    private Integer runtimeMinutes;
    @Column(length = 1000)
    private String genres;

    @OneToMany(mappedBy = "movie")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<MovieCelebrity> names;

    @OneToMany(mappedBy = "movie")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Country> countries;

    @OneToOne(mappedBy = "movie")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Rating rating;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setRuntimeMinutes(Integer runtimeMinutes) {
        this.runtimeMinutes = runtimeMinutes;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public void setNames(Set<MovieCelebrity> names) {
        this.names = names;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
