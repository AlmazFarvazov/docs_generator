package ru.itis.javalab.afarvazov.redis.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.itis.javalab.afarvazov.redis.models.RedisUser;

public interface RedisUserRepository extends KeyValueRepository<RedisUser, String> {
}
