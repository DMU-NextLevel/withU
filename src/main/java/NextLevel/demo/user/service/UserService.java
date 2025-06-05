package NextLevel.demo.user.service;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.img.service.ImgService;
import NextLevel.demo.role.UserRole;
import NextLevel.demo.user.dto.RequestUserCreateDto;
import NextLevel.demo.user.dto.user.RequestUpdatePasswordDto;
import NextLevel.demo.user.dto.user.RequestUpdateUserInfoDto;
import NextLevel.demo.user.entity.UserDetailEntity;
import NextLevel.demo.user.entity.UserEntity;
import NextLevel.demo.user.repository.UserDetailRepository;
import NextLevel.demo.user.repository.UserRepository;
import NextLevel.demo.util.StringUtil;
import NextLevel.demo.util.jwt.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final ImgService imgService;
    private final LoginService loginService;
    @Qualifier("passwordEncoder")
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public UserEntity getUserInfo(Long userId) {
        return userRepository.findUserFullInfoByUserId(userId).orElseThrow(
            ()->{throw new CustomException(ErrorCode.ACCESS_TOKEN_ERROR);}
        );
    }

    @Transactional
    public void updateUserInfo(RequestUpdateUserInfoDto dto, HttpServletRequest request, HttpServletResponse response) {
        UserEntity oldUser = getUserInfo(dto.getId());

        // email 변경 불가
        if(dto.getName().equals("email") || dto.getName().equals("id")) {
            throw new CustomException(ErrorCode.CAN_NOT_CHANGE_EMAIL);
        }

        switch(dto.getName()){
            case "email":
                throw new CustomException(ErrorCode.CAN_NOT_CHANGE_EMAIL);
            case "nickname":
                loginService.checkNickNameIsNotExist(dto.getValue());
                break;
        }

        try {
            Method setterMethod = oldUser.getClass().getDeclaredMethod(StringUtil.setGetterName(dto.getName()), String.class), String;
            setterMethod.invoke(oldUser, dto.getValue());
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.SIBAL_WHAT_IS_IT, e.getMessage());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.CAN_NOT_INVOKE, dto.getName());
        }

        oldUser.checkRole();
        userRepository.save(oldUser);

        jwtUtil.refreshAccessToken(request, response, oldUser.getRole());
    }

    @Transactional
    public void updateUserPassword(RequestUpdatePasswordDto dto) {
        UserEntity user = userRepository.findUserFullInfoByUserId(dto.getUserId()).orElseThrow(
            ()->{throw new CustomException(ErrorCode.ACCESS_TOKEN_ERROR);}
        );
        if(!passwordEncoder.matches(dto.getOldPassword(), user.getUserDetail().getPassword()))
            throw new CustomException(ErrorCode.LOGIN_FAILED);


        String newPassword = passwordEncoder.encode(dto.getNewPassword());

        log.info("passwd encode " + newPassword);

        userDetailRepository.updatePasswordByUserId(newPassword, user.getId());
    }

    @Transactional
    public void updateUserImg(Long userId, MultipartFile img) {
        UserEntity oldUser = userRepository.findById(userId).orElseThrow(
            ()->{throw new CustomException(ErrorCode.ACCESS_TOKEN_ERROR);}
        );
        if(img == null)
            throw new CustomException(ErrorCode.INPUT_REQUIRED_PARAMETER);

        ImgEntity imgEntity = imgService.updateImg(img, oldUser.getImg());

        if(oldUser.getImg() == null)
            oldUser.setImg(imgEntity);
    }

}
