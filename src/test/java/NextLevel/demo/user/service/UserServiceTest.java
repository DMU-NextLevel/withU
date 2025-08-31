package NextLevel.demo.user.service;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.img.service.ImgService;
import NextLevel.demo.user.dto.user.RequestUpdateUserInfoDto;
import NextLevel.demo.user.entity.UserEntity;
import NextLevel.demo.user.repository.UserDao;
import NextLevel.demo.user.repository.UserDetailRepository;
import NextLevel.demo.user.repository.UserRepository;
import NextLevel.demo.util.jwt.JWTUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDetailRepository userDetailRepository;
    @Mock
    private UserDao userDao;
    @Mock
    private ImgService imgService;
    @Spy
    private PasswordEncoder passwordEncoder;
    @Mock
    private JWTUtil jwtUtil;

    private UserEntity mockUser;

    @BeforeEach
    public void setUp() {
        mockUser = UserEntity
                .builder()
                .id(1L)
                .name("name")
                .nickName("nickname")
                .address("address")
                .point(100)
                .areaNumber("02-1111-1111")
                .number("010-1111-1111")
                .build();
    }

    @Test
    public void updateUserEmailOrNicknameTest() {
        Mockito.when(userDao.getUserInfo(Mockito.anyLong())).thenReturn(mockUser);
        RequestUpdateUserInfoDto updateEmailDto = new RequestUpdateUserInfoDto();
        updateEmailDto.setId(1L); updateEmailDto.setName("email"); updateEmailDto.setValue("updated eamil");
        RequestUpdateUserInfoDto updateNicknameDto = new RequestUpdateUserInfoDto();
        updateNicknameDto.setId(1L); updateNicknameDto.setName("nickName"); updateNicknameDto.setValue("updated nickname");

        Assertions.assertAll(
            ()->Assertions.assertThrows(
                CustomException.class,
                ()->userService.updateUserInfo(updateEmailDto, null, null)
            ),
            ()->Assertions.assertThrows(
                CustomException.class,
                ()->userService.updateUserInfo(updateNicknameDto, null, null)
            )
        );
    }
}
