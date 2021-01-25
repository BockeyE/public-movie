package me.lily.movie.service;

import me.lily.movie.domain.Rate;

import java.util.List;

public interface RateService {
    Rate save(Rate rate);
    List<Rate> findByUserId(Long userId);
    List<?> findAllGroupByMovieId();
}
