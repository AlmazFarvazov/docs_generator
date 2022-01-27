package ru.itis.javalab.afarvazov.redis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.javalab.afarvazov.redis.repository.BlackListRepository;

@Service
public class BlackListServiceImpl implements BlackListService {

    @Autowired
    private BlackListRepository blackListRepository;

    @Override
    public void add(String token) {
        blackListRepository.save(token);
    }

    @Override
    public boolean exists(String token) {
        return blackListRepository.exists(token);
    }
}
