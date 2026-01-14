package backend.Service;

import backend.Dto.UserDto;
import backend.Entities.User;
import backend.Request.Request;
import backend.Request.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface UserService {

    Flux<UserDto> findAll();
    Mono<User> findById(Long id);
    Mono<User> create(User user);
    Mono<User> update(User user);

    //Additional Method
    //Sign in request
    Mono<Response> signIn(Request request);
    //Check Current user logon detail
    Mono<UserDto> whoAmI();
    //Check current user logon ID
    Mono<Long> currentUser();


}
