package backend.ServiceImpl;

import backend.Dto.CurrentUser;
import backend.Dto.UserDto;
import backend.Entities.CustomUserDetails;
import backend.Entities.Role;
import backend.Entities.User;
import backend.Mapper.UserMapper;
import backend.Repository.EmployeeRepository;
import backend.Repository.UserRepository;
import backend.Request.Request;
import backend.Request.Response;
import backend.Service.UserService;
import backend.Utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Mono<Response> signIn(Request request) {
        if (request.username() == null || request.username().isBlank() || request.password() == null || request.password().isBlank()) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or password is required"));
        }

        return userRepository.findByUsername(request.username())
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found")))
                .flatMap(user -> {
                    if (!passwordEncoder.matches(request.password(), user.getPassword())) {
                        return Mono.error(new ResponseStatusException(
                                        HttpStatus.UNAUTHORIZED, "Invalid credentials"));
                    }
                    return Mono.just(new Response(jwtUtil.generateToken(user)));
                });
    }

    @Override
    public Flux<UserDto> findAll() {
        return userRepository.findAll()
                .map(UserMapper::toDto);
    }

    @Override
    public Mono<User> findById(Long id) {
        return userRepository.findById(id);
    }

//    @Override
//    public Mono<User> create(User user) {
//        if (user.getEmployeeId() == null) {
//            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST,"Employee ID is required"));
//        }
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//
//        return userRepository.save(User.from(user)
//                .role(Role.ROLE_USER)
//                .password(encodedPassword)
//                .employeeId(user.getEmployeeId())
//                .build())
//                .onErrorMap(
//                    DuplicateKeyException.class, ex -> new ResponseStatusException(
//                            HttpStatus.CONFLICT, "Employee ID already exist")
//                );
//    }

    @Override
    public Mono<User> create(User user) {
        var encodedPassword = passwordEncoder.encode(user.getPassword());

        return employeeRepository.findById(user.getEmployeeId())
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Employee not found"
                        )
                ))
                .flatMap(employee ->
                        userRepository.existsById(user.getEmployeeId())
                )
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(
                                new ResponseStatusException(
                                        HttpStatus.CONFLICT,
                                        "Employee already has a user account"
                                )
                        );
                    }

                    return userRepository.save(
                            User.from(user)
                                    .role(Role.ROLE_USER)
                                    .password(encodedPassword)
                                    .employeeId(user.getEmployeeId())
                                    .build()
                    );
                });
    }


    @Override
    public Mono<User> update(User user) {
        return userRepository.findById(user.getId())
                .flatMap(existingUser -> {
                    User.update(existingUser)
                            .setUpdatedDate(LocalDateTime.now());

                    return userRepository.save(existingUser);
                });
    }

    @Override
    public Mono<UserDto> whoAmI() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .flatMap(auth -> {
                    String username = auth.getName();
                    return userRepository.findByUsername(username)
                            .map(user -> new UserDto(user.getId(), user.getUsername(), user.getRole()));
                });
    }

    @Override
    public Mono<Long> currentUser() {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> (CustomUserDetails) ctx.getAuthentication().getPrincipal())
                .map(user -> new CurrentUser(user.getId()).id());
    }
}
