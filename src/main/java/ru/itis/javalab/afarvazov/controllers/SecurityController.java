package ru.itis.javalab.afarvazov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalab.afarvazov.dto.TokenDto;
import ru.itis.javalab.afarvazov.dto.form.SignInForm;
import ru.itis.javalab.afarvazov.dto.form.SignUpForm;
import ru.itis.javalab.afarvazov.services.UsersService;

@RestController
@RequiredArgsConstructor
public class SecurityController {

    private final UsersService usersService;

    @PreAuthorize("permitAll()")
    @PostMapping("/signUp")
    public ResponseEntity signUp(@RequestBody SignUpForm signUpForm) {
        usersService.signUp(signUpForm);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/signIn")
    public ResponseEntity<TokenDto> singIn(@RequestBody SignInForm signInForm) {
        TokenDto tokenDto = usersService.signIn(signInForm);
        return ResponseEntity.ok(tokenDto);
    }

}
