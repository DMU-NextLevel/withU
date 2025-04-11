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
    private final EmailService emailService;

    // use refresh token filter
    public UserDetailEntity findUserDetailByUserId(Long userId) {
        return userDetailRepository.findUserDetailByUserId(userId);
    }

    @Transactional
    public void socialLogin(RequestUserCreateDto socialLoginDto, HttpServletResponse response) {
        String socialProvider = socialLoginDto.getSocialProvider();
        String socialId = socialLoginDto.getSocialId();

        Optional<UserDetailEntity> userDetailOptional = userDetailRepository.findBySocialProviderAndSocialId(socialProvider, socialId);
        UserDetailEntity userDetail = null;
        if(userDetailOptional.isEmpty()) {
            UserEntity user = userRepository.save(socialLoginDto.toUserEntity());

            UserDetailEntity userDetailEntity = socialLoginDto.toUserDetailEntity(user);
            userDetailEntity.setRole(UserRole.SOCIAL.name());
            userDetail = userDetailRepository.save(userDetailEntity);
        }else{
            userDetail = userDetailOptional.get();
        }

        jwtUtil.addRefresh(response, userDetail.getUser().getId(), userDetail.getUUID());
    }

    // user UserController : post register
    @Transactional
    public UserDetailEntity register(RequestUserCreateDto dto) {
        if(!checkEmailIsNotExist(dto.getEmail()))
            throw new CustomException(ErrorCode.ALREADY_EXISTS_EMAIL);

        emailService.checkEmailKeyAtRegister(dto.getEmail(), dto.getKey());

        if(dto.validateAllData())
            dto.setRole(UserRole.USER.name());

        UserEntity user = userRepository.save(dto.toUserEntity());
        UserDetailEntity userDetail = userDetailRepository.save(dto.toUserDetailEntity(user));

        return userDetail;
    }

    public boolean checkEmailIsNotExist(String email) {
        if(userDetailRepository.findByEmail(email).isEmpty())
            return true;
        else
            return false;
    }

    @Transactional
    public UserDetailEntity login(RequestUserLoginDto loginDto) {
        Optional<UserDetailEntity> user = userDetailRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());

        if(user.isEmpty()) {
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }
        return user.get();
    }

    // check nickname
    public boolean checkNickName(String nickName) {
        return userRepository.findUserByNickName(nickName).isEmpty();
    }
}
