package com.sideagroup.academy.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "movie_celebrity")
public class MovieCelebrity {
    @EmbeddedId
    private MovieCelebrityKey id;

    @ManyToOne
    @MapsId("celebrityId")
    private Celebrity celebrity;

    @ManyToOne
    @MapsId("movieId")
    private Movie movie;

    @Column(length = 1000)
    private String category;

    @Column(length = 1000)
    private String characters;

    public MovieCelebrity(MovieCelebrityKey id) { this.id = id; }

    public MovieCelebrity() {
        this(null);
    }

    public void setId(MovieCelebrityKey id) {
        this.id = id;
    }

    public void setCelebrity(Celebrity celebrity) {
        this.celebrity = celebrity;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }
}
