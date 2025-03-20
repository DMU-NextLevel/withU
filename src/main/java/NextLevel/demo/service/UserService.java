package NextLevel.demo.service;

import NextLevel.demo.dto.UserDto.RequestUserCreateDto;
import NextLevel.demo.dto.UserDto.RequestUserLoginDto;
import NextLevel.demo.entity.UserDetailEntity;
import NextLevel.demo.entity.UserEntity;
import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.repository.UserDetailRepository;
import NextLevel.demo.repository.UserRepository;
import NextLevel.demo.role.UserRole;
import NextLevel.demo.util.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final JWTUtil jwtUtil;

    // use refresh token filter
    public UserDetailEntity findUserDetailByUserId(Long userId) {
        return userRepository.findUserDetailByUserId(userId);
    }

    // user UserController : post register
    @Transactional
    public void register(RequestUserCreateDto requestUserCreateDto, HttpServletResponse response) {
        UserEntity user = userRepository.save(requestUserCreateDto.toUserEntity());
        UserDetailEntity userDetail = userDetailRepository.save(requestUserCreateDto.toUserDetailEntity(user.getId()));

        jwtUtil.addRefresh(response, user.getId(), userDetail.getUUID());
    }

    @Transactional
    public void login(RequestUserLoginDto loginDto, HttpServletResponse response) {
        Optional<UserDetailEntity> user = userDetailRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());

        if(user.isEmpty()) {
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }
        UserDetailEntity userDetail = user.get();

        jwtUtil.addRefresh(response, userDetail.getId(), userDetail.getUUID());
    }

    @Transactional
    public void socailLogin(RequestUserCreateDto socialLoginDto, HttpServletResponse response) {
        String socialProvider = socialLoginDto.getSocialProvider();
        String socialId = socialLoginDto.getSocialId();

        Optional<UserDetailEntity> userDetailOptional = userDetailRepository.findBySocialProviderAndSocialId(socialProvider, socialId);
        UserDetailEntity userDetail = null;
        if(userDetailOptional.isEmpty()) {

            UserEntity user = userRepository.save(socialLoginDto.toUserEntity());

            UserDetailEntity userDetailEntity = socialLoginDto.toUserDetailEntity(user.getId());
            userDetailEntity.setRole(UserRole.SOCIAL.name());
            userDetail = userDetailRepository.save(userDetailEntity);
        }else{
            userDetail = userDetailOptional.get();
        }

        jwtUtil.addRefresh(response, userDetail.getId(), userDetail.getUUID());
    }
}
