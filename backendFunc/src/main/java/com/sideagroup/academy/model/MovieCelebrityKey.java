package com.sideagroup.academy.model;


import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.Length;

import java.io.Serializable;

/* Usando embeddable si possono creare chiavi composte da usare nelle tabelle*/
@Getter
@Embeddable
public class MovieCelebrityKey implements Serializable {
@Column(length = 200)
    private String celebrityId;

@Column(length = 200)
    private String movieId;

public MovieCelebrityKey() { this(null, null); }

    public MovieCelebrityKey(String celebrityId, String movieId) {
            this.celebrityId = celebrityId;
            this.movieId = movieId;
    }

    public void setCelebrityId(String celebrityId) {
        this.celebrityId = celebrityId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
