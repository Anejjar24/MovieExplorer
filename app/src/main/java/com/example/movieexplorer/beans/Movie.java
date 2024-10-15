package com.example.movieexplorer.beans;

public class Movie {
    private int id;
    private String name;
    private String img;
    private float star;
    private String genre;
    private String director;
    private String principalActor;
    private static int comp;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Movie(String name, String img, float star, String genre, String director, String principalActor, String description) {
        this.id = comp++;
        this.name = name;
        this.img = img;
        this.star = star;
        this.genre = genre;
        this.director = director;
        this.principalActor = principalActor;
        this.description = description;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPrincipalActor() {
        return principalActor;
    }

    public void setPrincipalActor(String principalActor) {
        this.principalActor = principalActor;
    }
}
