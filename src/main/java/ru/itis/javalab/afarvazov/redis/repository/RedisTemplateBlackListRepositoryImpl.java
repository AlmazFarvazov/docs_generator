package ru.itis.javalab.afarvazov.redis.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisTemplateBlackListRepositoryImpl implements BlackListRepository {

    @Autowired
    private RedisTemplate<String, String> template;

    @Override
    public void save(String token) {
        template.opsForValue().set(token, "");
    }

    @Override
    public boolean exists(String token) {
        return Boolean.TRUE.equals(template.hasKey(token));
    }
}
