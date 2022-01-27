package ru.itis.javalab.afarvazov.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.DocumentException;
import freemarker.template.TemplateException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.itis.javalab.afarvazov.dto.UserTemplateDto;

import java.io.IOException;

@Component
public class PdfConsumerService {

    private final DocumentsService pdfDocumentsService;
    private final ObjectMapper objectMapper;

    public PdfConsumerService(DocumentsService pdfDocumentsService) {
        this.pdfDocumentsService = pdfDocumentsService;
        this.objectMapper = new ObjectMapper();
    }

    @RabbitListener(queues = {"queue_welcome"}, containerFactory = "containerFactory")
    public byte[] generatePdf(Message message) {

        UserTemplateDto dto;
        try {
            dto = objectMapper.readValue(message.getBody(), UserTemplateDto.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("JSON not valid");
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
        try {
            return pdfDocumentsService.generateWelcomeDocument(dto);
        } catch (IOException | TemplateException | DocumentException e) {
            throw new IllegalStateException(e.getMessage());
        }

    }

}
