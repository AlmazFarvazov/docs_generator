package ru.itis.javalab.afarvazov.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.javalab.afarvazov.dto.TokenDto;
import ru.itis.javalab.afarvazov.dto.form.SignInForm;
import ru.itis.javalab.afarvazov.dto.form.SignUpForm;
import ru.itis.javalab.afarvazov.exceptions.InvalidUsernameOrPasswordException;
import ru.itis.javalab.afarvazov.exceptions.NoSuchUserException;
import ru.itis.javalab.afarvazov.models.User;
import ru.itis.javalab.afarvazov.redis.services.RedisUserService;
import ru.itis.javalab.afarvazov.repositories.UsersRepository;
import ru.itis.javalab.afarvazov.security.jwt.util.TokenUtil;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisUserService userService;
    private final TokenUtil tokenService;

    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder,
                            RedisUserService userService, TokenUtil tokenService) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public User getUserByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new NoSuchUserException("User not found"));
    }

    @Override
    public User getUserByUsername(String username) {
        return usersRepository.findByUsername(username).orElseThrow(() -> new NoSuchUserException("User not found"));
    }

    @Override
    public void saveUser(User user) {
        usersRepository.save(user);
    }

    @Override
    public void signUp(SignUpForm signUpForm) {
        User user = User.builder()
                .username(signUpForm.getUsername())
                .email(signUpForm.getEmail())
                .hashPassword(passwordEncoder.encode(signUpForm.getPassword()))
                .state(User.State.ACTIVE)
                .role(User.Role.USER)
                .build();
        usersRepository.save(user);
    }

    @Override
    public TokenDto signIn(SignInForm signInForm) {
        User user = getUserByUsername(signInForm.getUsername());
        if (passwordEncoder.matches(signInForm.getPassword(), user.getHashPassword())) {
            TokenDto tokenDto = TokenDto.builder()
                    .value(tokenService.generateToken(user))
                    .build();
            userService.addTokenToUser(user, tokenDto.getValue());
            return tokenDto;
        }
        else {
            throw new InvalidUsernameOrPasswordException("Invalid credentials");
        }
    }

}
