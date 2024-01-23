package com.sideagroup.academy.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

@Getter
@Entity
@Table(name = "celebrity")
public class Celebrity {
    @Id
    @Column(length=200)
    private String id;
    @Column(length = 1000, nullable = false)
    private String primaryName;
    private Integer birthYear;
    private Integer deathYear;

    @OneToMany(mappedBy = "celebrity")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<MovieCelebrity> titles;

    public void setId(String id) {
        this.id = id;
    }

    public void setPrimaryName(String primaryName) {
        this.primaryName = primaryName;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public void setTitles(Set<MovieCelebrity> titles) {
        this.titles = titles;
    }
}
