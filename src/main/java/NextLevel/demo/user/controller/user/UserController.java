package NextLevel.demo.user.controller.user;

import NextLevel.demo.common.SuccessResponse;
import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.img.service.ImgService;
import NextLevel.demo.user.dto.RequestUserCreateDto;
import NextLevel.demo.user.dto.user.ResponseUserInfoDto;
import NextLevel.demo.user.entity.UserEntity;
import NextLevel.demo.user.service.LoginService;
import NextLevel.demo.user.service.UserService;
import NextLevel.demo.util.jwt.JWTUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/social/user")
public class UserController {
    private final UserService userService;
    private final ImgService imgService;
    private final LoginService loginService;

    @GetMapping
    public ResponseEntity<?> getUserInfo() {
        Long userId = JWTUtil.getUserIdFromSecurityContext();

        UserEntity user = userService.getUserInfo(userId);

        ResponseUserInfoDto dto = ResponseUserInfoDto.of(user);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success", dto));
    }

    @PutMapping
    public ResponseEntity<?> updateUserInfo(@ModelAttribute @Valid RequestUserCreateDto dto) {
        Long userId = JWTUtil.getUserIdFromSecurityContext();
        dto.setId(userId);

        userService.updateUserInfo(dto);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success", null));
    }

    // password 변경

}
