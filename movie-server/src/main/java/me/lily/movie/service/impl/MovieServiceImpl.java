package me.lily.movie.service.impl;

import me.lily.movie.domain.Movie;
import me.lily.movie.repository.MovieRepository;
import me.lily.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public Page<Movie> findAll(Integer page,Integer size) {
        return movieRepository.findAll(new QPageRequest(page,size));
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }
    @Override
    public Movie getOne(Long id) {

        return movieRepository.getOne(id);

    }
}
