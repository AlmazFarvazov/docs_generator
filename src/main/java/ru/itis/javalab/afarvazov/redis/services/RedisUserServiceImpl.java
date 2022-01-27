package ru.itis.javalab.afarvazov.redis.services;

import org.springframework.stereotype.Service;
import ru.itis.javalab.afarvazov.models.User;
import ru.itis.javalab.afarvazov.redis.models.RedisUser;
import ru.itis.javalab.afarvazov.redis.repository.RedisUserRepository;
import ru.itis.javalab.afarvazov.repositories.UsersRepository;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class RedisUserServiceImpl implements RedisUserService {
    private final UsersRepository usersRepository;
    private final RedisUserRepository redisUsersRepository;

    public RedisUserServiceImpl(UsersRepository usersRepository, RedisUserRepository redisUsersRepository) {
        this.usersRepository = usersRepository;
        this.redisUsersRepository = redisUsersRepository;
    }

    @Override
    public void addTokenToUser(User user, String token) {
        String redisId = user.getRedisId();

        RedisUser redisUser;
        if (redisId != null && !redisId.equals("")) {
            redisUser = redisUsersRepository.findById(redisId).orElseThrow(IllegalArgumentException::new);
            if (redisUser.getTokens() == null) {
                redisUser.setTokens(new ArrayList<>());
            }
            redisUser.getTokens().add(token);
        } else {
            redisUser = RedisUser.builder()
                    .userId(user.getId())
                    .tokens(Collections.singletonList(token))
                    .build();
        }
        redisUsersRepository.save(redisUser);
        user.setRedisId(redisUser.getId());
        usersRepository.save(user);
    }
}
