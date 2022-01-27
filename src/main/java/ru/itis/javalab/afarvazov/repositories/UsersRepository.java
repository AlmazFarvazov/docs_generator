package ru.itis.javalab.afarvazov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.afarvazov.models.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}
