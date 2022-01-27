package ru.itis.javalab.afarvazov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.javalab.afarvazov.models.Document;
import ru.itis.javalab.afarvazov.models.User;

import java.util.List;

public interface DocumentsRepository extends JpaRepository<Document, Long> {
    List<Document> findByUser(User user);
}
