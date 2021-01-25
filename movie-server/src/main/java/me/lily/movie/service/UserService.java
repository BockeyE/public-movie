package me.lily.movie.service;

import me.lily.movie.domain.User;

public interface UserService {
    User findByUserName(String userName);

    User save(User user);
}
