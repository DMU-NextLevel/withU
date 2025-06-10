package NextLevel.demo.user.service;

import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.img.service.ImgService;
import NextLevel.demo.user.dto.RequestUserCreateDto;
import NextLevel.demo.user.dto.login.RequestUserLoginDto;
import NextLevel.demo.user.entity.UserDetailEntity;
import NextLevel.demo.user.entity.UserEntity;
import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.user.repository.UserDetailRepository;
import NextLevel.demo.user.repository.UserRepository;
import NextLevel.demo.util.jwt.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final JWTUtil jwtUtil;
    private final EmailService emailService;
    private final ImgService imgService;
    @Qualifier("passwordEncoder")
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDetailEntity socialLogin(RequestUserCreateDto socialLoginDto) {
        String socialProvider = socialLoginDto.getSocialProvider();
        String socialId = socialLoginDto.getSocialId();

        Optional<UserDetailEntity> userDetailOptional = userDetailRepository.findBySocialProviderAndSocialId(socialProvider, socialId);
        UserDetailEntity userDetail = null;
        if(userDetailOptional.isEmpty()) {
            // email, nick name 검증
            checkEmailAndNickNameElseThrow(socialLoginDto.getEmail(), socialLoginDto.getNickName());

            UserEntity user = userRepository.save(socialLoginDto.toUserEntity());

            UserDetailEntity userDetailEntity = socialLoginDto.toUserDetailEntity(user);
            userDetail = userDetailRepository.save(userDetailEntity);

            userDetail.setUser(user);
        }else{
            userDetail = userDetailOptional.get();
        }

        return userDetail;
    }

    // user UserController : post register
    @Transactional
    public UserDetailEntity register(RequestUserCreateDto dto) {
        checkEmailAndNickNameElseThrow(dto.getEmail(), dto.getNickName());

        // save img get uri
        ImgEntity savedImg = null;
        try {
            savedImg = imgService.saveImg(dto.getImg());
        }catch (CustomException e) {;}
        dto.setImgEntity(savedImg);

        // key 값 null 체크
        if(dto.getKey() == null || dto.getKey().isEmpty())
            throw new CustomException(ErrorCode.INPUT_REQUIRED_PARAMETER);

        emailService.checkEmailKeyAtRegister(dto.getEmail(), dto.getKey());

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        UserEntity user = userRepository.save(dto.toUserEntity());
        UserDetailEntity userDetail = userDetailRepository.save(dto.toUserDetailEntity(user));

        return userDetail;
    }

    @Transactional
    public UserDetailEntity login(RequestUserLoginDto loginDto) {
        Optional<UserDetailEntity> user = userDetailRepository.findByEmail(loginDto.getEmail());

        if(user.isEmpty() || !passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword())) {
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }
        return user.get();
    }

    // check email nick name  else throw CustomException
    public void checkEmailAndNickNameElseThrow(String email, String nickName) {
        if(!checkEmailIsNotExist(email))
            throw new CustomException(ErrorCode.ALREADY_EXISTS_EMAIL);
        if(!checkNickNameIsNotExist(nickName))
            throw new CustomException(ErrorCode.ALREADY_EXISTS_NICKNAME);
    }

    public boolean checkEmailIsNotExist(String email) {
        return userDetailRepository.findByEmail(email).isEmpty();
    }

    public boolean checkNickNameIsNotExist(String nickName) {
        return userRepository.findUserByNickName(nickName).isEmpty();
    }

    @Transactional
    public void updateTempPassword(String email) {
        UserEntity user = userRepository.findUserByEmail(email).orElseThrow(
            ()->{throw new CustomException(ErrorCode.NOT_FOUND, "email");}
        );

        String randomPassword = Base64.getEncoder().encodeToString( String.valueOf(new Random().nextDouble()).getBytes() );
        log.info("email : " + email + " new random password : " + randomPassword);

        // emailService.sendEmailCode(email, );

        user.getUserDetail().setPassword(randomPassword);
    }
}
