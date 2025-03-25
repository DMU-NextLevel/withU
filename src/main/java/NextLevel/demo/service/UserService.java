package NextLevel.demo.service;

import NextLevel.demo.dto.UserDto.ResponseUserInfoDto;
import NextLevel.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public ResponseUserInfoDto getUserInfo(Long userId) {
        return ResponseUserInfoDto.of(userRepository.findUserFullInfoByUserId(userId));
    }
}
