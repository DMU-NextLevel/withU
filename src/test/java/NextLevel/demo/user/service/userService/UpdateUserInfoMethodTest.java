package NextLevel.demo.user.service.userService;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.img.service.ImgService;
import NextLevel.demo.user.dto.user.RequestUpdateUserInfoDto;
import NextLevel.demo.user.entity.UserEntity;
import NextLevel.demo.user.repository.UserDao;
import NextLevel.demo.user.repository.UserDetailRepository;
import NextLevel.demo.user.repository.UserRepository;
import NextLevel.demo.user.service.UserService;
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
public class UpdateUserInfoMethodTest {

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

    @Test
    public void updateInvalidNumberTest() {
        Mockito.when(userDao.getUserInfo(Mockito.anyLong())).thenReturn(mockUser);
        RequestUpdateUserInfoDto updateNumberDto = new RequestUpdateUserInfoDto();
        updateNumberDto.setId(1L); updateNumberDto.setName("number"); updateNumberDto.setValue("111-11-11");

        RequestUpdateUserInfoDto updateAreaNumberDto = new RequestUpdateUserInfoDto();
        updateAreaNumberDto.setId(1L); updateAreaNumberDto.setName("areaNumber"); updateAreaNumberDto.setValue("00-00-00");

        Assertions.assertAll(
                ()->Assertions.assertThrows(
                        CustomException.class,
                        ()->userService.updateUserInfo(updateNumberDto, null, null)
                ),
                ()->Assertions.assertThrows(
                        CustomException.class,
                        ()->userService.updateUserInfo(updateAreaNumberDto, null, null)
                )
        );
    }

    @Test
    public void updateAlreadyExistNickName() {
        Mockito.when(userDao.getUserInfo(Mockito.anyLong())).thenReturn(mockUser);
        Mockito.when(userDao.checkNickNameIsNotExist(Mockito.anyString())).thenReturn(false);
        RequestUpdateUserInfoDto updateNameDto = new RequestUpdateUserInfoDto();
        updateNameDto.setId(1L); updateNameDto.setName("nickName"); updateNameDto.setValue("updated nickname");

        Assertions.assertThrows(
                CustomException.class,
                ()->userService.updateUserInfo(updateNameDto, null, null)
        );
    }

    @Test
    public void updateUserInfoTest() {
        Mockito.when(userDao.getUserInfo(Mockito.anyLong())).thenReturn(mockUser);
        Mockito.when(userDao.checkNickNameIsNotExist(Mockito.anyString())).thenReturn(true);
        RequestUpdateUserInfoDto updateNameDto = new RequestUpdateUserInfoDto();
        updateNameDto.setId(1L); updateNameDto.setName("nickName"); updateNameDto.setValue("updated nickname");

        UserEntity updatedUser = userService.updateUserInfo(updateNameDto, null, null);

        Assertions.assertEquals("updated nickname", updatedUser.getNickName());
    }

    @Test
    public void updatePointTest() {
        Mockito.when(userDao.getUserInfo(Mockito.anyLong())).thenReturn(mockUser);
        RequestUpdateUserInfoDto updatePointDto = new RequestUpdateUserInfoDto();
        updatePointDto.setId(1L); updatePointDto.setName("point"); updatePointDto.setValue("5");

        CustomException notFoundMethod = Assertions.assertThrows(
                CustomException.class,
                ()->userService.updateUserInfo(updatePointDto, null, null),
                "userService :: updateUserInfo :: refraction :: getSetterMethod('', String.class) 해당 부분 수정됨?"
        );
        Assertions.assertEquals(notFoundMethod.errorCode, ErrorCode.CAN_NOT_INVOKE);
    }
}
