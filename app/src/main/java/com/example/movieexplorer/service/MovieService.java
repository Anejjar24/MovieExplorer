package com.example.movieexplorer.service;

import com.example.movieexplorer.beans.Movie;
import com.example.movieexplorer.dao.IDao;

import java.util.ArrayList;
import java.util.List;

public class MovieService implements IDao<Movie> {
    private List<Movie> movies;
    private static MovieService instance;
    private MovieService() {
        this.movies = new ArrayList<>();
    }

    public static MovieService getInstance() {
        if(instance == null)
            instance = new MovieService();

        return instance;
    }



    @Override
    public boolean create(Movie o) {
        return movies.add(o);
    }

    @Override
    public boolean delete(Movie o) {
        return movies.remove(o);
    }

    @Override
    public boolean update(Movie o) {
        for(Movie movie:movies){
            if(movie.getId()==o.getId()){
                movie.setImg(o.getImg());
                movie.setName(o.getName());
                movie.setGenre(o.getGenre());
                movie.setDirector(o.getDirector());
                movie.setStar(o.getStar());
                movie.setPrincipalActor(o.getPrincipalActor());
                movie.setGenre(o.getGenre());

            }

        }
        return false;
    }

    @Override
    public List<Movie> findAll() {
        return movies;
    }

    @Override
    public Movie findById(int id) {
        for(Movie movie :movies){
            if(movie.getId()==id)
                return movie;
        }
        return null;
    }
}
