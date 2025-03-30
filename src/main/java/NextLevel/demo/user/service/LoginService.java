package NextLevel.demo.user.service;

import NextLevel.demo.user.dto.RequestUserCreateDto;
import NextLevel.demo.user.dto.login.RequestUserLoginDto;
import NextLevel.demo.user.entity.UserDetailEntity;
import NextLevel.demo.user.entity.UserEntity;
import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.user.repository.UserDetailRepository;
import NextLevel.demo.user.repository.UserRepository;
import NextLevel.demo.role.UserRole;
import NextLevel.demo.util.jwt.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final JWTUtil jwtUtil;

    // use refresh token filter
    public UserDetailEntity findUserDetailByUserId(Long userId) {
        return userDetailRepository.findUserDetailByUserId(userId);
    }

    // user UserController : post register
    @Transactional
    public UserDetailEntity register(RequestUserCreateDto requestUserCreateDto) {
        if(userDetailRepository.findByEmail(requestUserCreateDto.getEmail()).isPresent())
            throw new CustomException(ErrorCode.ALREADY_EXISTS);

        UserEntity user = userRepository.save(requestUserCreateDto.toUserEntity());
        UserDetailEntity userDetail = userDetailRepository.save(requestUserCreateDto.toUserDetailEntity(user.getId()));

        return userDetail;
    }

    @Transactional
    public UserDetailEntity login(RequestUserLoginDto loginDto) {
        Optional<UserDetailEntity> user = userDetailRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());

        if(user.isEmpty()) {
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }
        return user.get();
    }

    @Transactional
    public void socialLogin(RequestUserCreateDto socialLoginDto, HttpServletResponse response) {
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
