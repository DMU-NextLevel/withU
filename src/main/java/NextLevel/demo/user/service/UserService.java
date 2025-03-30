package NextLevel.demo.user.service;

import NextLevel.demo.user.dto.RequestUserCreateDto;
import NextLevel.demo.user.dto.user.ResponseUserInfoDto;
import NextLevel.demo.user.entity.UserEntity;
import NextLevel.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity getUserInfo(Long userId) {
        return userRepository.findUserFullInfoByUserId(userId);
    }

    public UserEntity updateUserInfo(RequestUserCreateDto dto) {
        return userRepository.save(dto.toUserEntity());
    }

}
