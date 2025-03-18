package NextLevel.demo.service;

import NextLevel.demo.dto.UserDetailRequestDto;
import NextLevel.demo.dto.UserDto.RequestUserCreateDto;
import NextLevel.demo.model.UserDetail;
import NextLevel.demo.repository.UserRepository;
import NextLevel.demo.repository.UserTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserTransaction userTransaction;

    // use refresh token filter
    public UserDetail findUserDetailByUserId(Long userId) {
        return userRepository.findUserDetailByUserId(userId);
    }

    // user UserController : post login
    public void login(RequestUserCreateDto requestUserCreateDto) {
        userTransaction.login(requestUserCreateDto);
    }
}
