package NextLevel.demo.user.service.userService;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.img.service.ImgService;
import NextLevel.demo.role.UserRole;
import NextLevel.demo.user.dto.user.RequestUpdatePasswordDto;
import NextLevel.demo.user.entity.UserDetailEntity;
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
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UpdateUserPasswordMethodTest {
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
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Mock
    private JWTUtil jwtUtil;

    private UserEntity mockUser;
    private UserDetailEntity mockUserDetail;

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
        mockUserDetail = UserDetailEntity
                .builder()
                .email("email")
                .password("password")
                .role(UserRole.USER.name())
                .build();
    }

    @Test
    public void updatePasswordSuccess() {
        mockUserDetail.setPassword("password");
        mockUser.setUserDetail(mockUserDetail);
        Mockito.when(userRepository.findUserFullInfoByUserId(Mockito.anyLong())).thenReturn(Optional.of(mockUser));

        RequestUpdatePasswordDto invalidPasswordDto = new RequestUpdatePasswordDto(1L, "strange password", "newPassword");

        Assertions.assertThrows(
            CustomException.class,
            ()->userService.updateUserPassword(invalidPasswordDto)
        );

    }

    @Test
    public void updatePasswordFail() {
        String oldPassword = "oldPassword"; String newPassword = "newPassword";
        String oldPasswordIncoded = passwordEncoder.encode(oldPassword);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        mockUserDetail.setPassword(oldPasswordIncoded);
        mockUser.setUserDetail(mockUserDetail);
        Mockito.when(userRepository.findUserFullInfoByUserId(Mockito.anyLong())).thenReturn(Optional.of(mockUser));

        RequestUpdatePasswordDto passwordDto = new RequestUpdatePasswordDto(1L, oldPassword, newPassword);

        userService.updateUserPassword(passwordDto);
        Mockito.verify(passwordEncoder).matches(Mockito.eq(oldPassword), Mockito.eq(oldPasswordIncoded));
        Mockito.verify(userDetailRepository).updatePasswordByUserId(argumentCaptor.capture() , Mockito.any());

        Assertions.assertTrue(passwordEncoder.matches(newPassword, argumentCaptor.getValue()));
    }
}
