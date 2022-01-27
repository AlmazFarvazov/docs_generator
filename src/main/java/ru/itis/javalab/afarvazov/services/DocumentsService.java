package ru.itis.javalab.afarvazov.services;

import com.lowagie.text.DocumentException;
import freemarker.template.TemplateException;
import ru.itis.javalab.afarvazov.dto.UserTemplateDto;
import ru.itis.javalab.afarvazov.models.User;

import java.io.IOException;

public interface DocumentsService {
    byte[] generateWelcomeDocument(UserTemplateDto userTemplateDto) throws IOException, TemplateException, DocumentException;
    void save(UserTemplateDto userTemplateDto, User user);
}
