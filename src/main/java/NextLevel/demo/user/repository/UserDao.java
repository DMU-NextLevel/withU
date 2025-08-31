package NextLevel.demo.user.repository;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.user.entity.UserDetailEntity;
import NextLevel.demo.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDao {
    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;

    public UserEntity getUserInfo(Long userId) {
        return userRepository.findUserFullInfoByUserId(userId).orElseThrow(
                ()->{throw new CustomException(ErrorCode.ACCESS_TOKEN_ERROR);}
        );
    }

    public Optional<UserDetailEntity> findBySocialProviderAndSocialId(String socialProvider, String socialId) {
        return userDetailRepository.findBySocialProviderAndSocialId(socialProvider, socialId);
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

}
