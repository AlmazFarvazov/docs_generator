package ru.itis.javalab.afarvazov.redis.repository;

public interface BlackListRepository {

    void save(String token);

    boolean exists(String token);

}
