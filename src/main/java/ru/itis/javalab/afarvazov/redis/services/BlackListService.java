package ru.itis.javalab.afarvazov.redis.services;

public interface BlackListService {

    void add(String token);

    boolean exists(String token);

}
