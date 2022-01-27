package ru.itis.javalab.afarvazov.services;

import com.lowagie.text.DocumentException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;
import ru.itis.javalab.afarvazov.dto.UserTemplateDto;
import ru.itis.javalab.afarvazov.models.Document;
import ru.itis.javalab.afarvazov.models.User;
import ru.itis.javalab.afarvazov.repositories.DocumentsRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class PdfDocumentsServiceImpl implements DocumentsService {

    private final DocumentsRepository repository;

    private final Configuration configuration;

    public PdfDocumentsServiceImpl(DocumentsRepository repository, Configuration configuration) {
        this.repository = repository;
        this.configuration = configuration;
    }

    @Override
    public byte[] generateWelcomeDocument(UserTemplateDto userTemplateDto) {
        Template template = null;
        try {
            template = configuration.getTemplate("template.ftlh");
        }  catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("user", userTemplateDto);
        StringWriter stringWriter = new StringWriter();
        ITextRenderer renderer = new ITextRenderer();
        try {
            template.process(attributes, stringWriter);
        }  catch (IOException | TemplateException e) {
            throw new IllegalStateException(e.getMessage());
        }
        renderer.setDocumentFromString(stringWriter.toString());
        renderer.layout();
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        try {
            renderer.createPDF(pdfOutputStream);
        }  catch (DocumentException e) {
            throw new IllegalStateException(e.getMessage());
        }
        return pdfOutputStream.toByteArray();
    }

    @Override
    public void save(UserTemplateDto userTemplateDto, User user) {
        Document document = Document.builder()
                .user(user)
                .data(generateWelcomeDocument(userTemplateDto))
                .createdAt(LocalDateTime.now())
                .type(Document.Type.WELCOME)
                .build();
        repository.save(document);
    }
}
