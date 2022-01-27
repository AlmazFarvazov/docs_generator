package ru.itis.javalab.afarvazov.services;

import ru.itis.javalab.afarvazov.dto.TokenDto;
import ru.itis.javalab.afarvazov.dto.form.SignInForm;
import ru.itis.javalab.afarvazov.dto.form.SignUpForm;
import ru.itis.javalab.afarvazov.models.User;

public interface UsersService {
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    void saveUser(User user);
    void signUp(SignUpForm signUpForm);
    TokenDto signIn(SignInForm signInForm);
}
