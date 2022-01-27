package ru.itis.javalab.afarvazov.redis.services;

import ru.itis.javalab.afarvazov.models.User;

public interface RedisUserService {

    void addTokenToUser(User user, String token);

}
