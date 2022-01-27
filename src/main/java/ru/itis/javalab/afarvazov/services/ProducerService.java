package ru.itis.javalab.afarvazov.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import ru.itis.javalab.afarvazov.dto.UserTemplateDto;

import java.util.Optional;

@Component
public class ProducerService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final RabbitTemplate rabbitTemplate;
    private final Exchange exchange;

    public ProducerService(RabbitTemplate rabbitTemplate, Exchange exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    public byte[] generatePdf(UserTemplateDto dto) {
        try {
            String userJson = mapper.writeValueAsString(dto);
            Optional<Message> msg = Optional.ofNullable(rabbitTemplate.sendAndReceive(exchange.getName(), "welcome",
                    new Message(userJson.getBytes())));
            return msg.orElseThrow(IllegalArgumentException::new).getBody();
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException();
        }
    }
}
