package ru.itis.javalab.afarvazov.controllers;

import com.lowagie.text.DocumentException;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalab.afarvazov.dto.UserTemplateDto;
import ru.itis.javalab.afarvazov.security.details.UserDetailsImpl;
import ru.itis.javalab.afarvazov.security.jwt.auth.JwtTokenAuthentication;
import ru.itis.javalab.afarvazov.services.DocumentsService;
import ru.itis.javalab.afarvazov.services.ProducerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/documents/pdf")
public class PdfDocumentController {

    private final DocumentsService documentsService;
    private final ProducerService producerService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping(value = "/welcome", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdfForAddress(@RequestBody UserTemplateDto userTemplateDto) {
        JwtTokenAuthentication authentication = (JwtTokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
        byte[] body = producerService.generatePdf(userTemplateDto);
        documentsService.save(userTemplateDto, authentication.getUserDetails().getUser());
        return ResponseEntity.ok(body);
    }

}
