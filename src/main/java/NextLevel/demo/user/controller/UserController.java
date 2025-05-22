package NextLevel.demo.user.controller;

import NextLevel.demo.common.SuccessResponse;
import NextLevel.demo.user.dto.RequestUserCreateDto;
import NextLevel.demo.user.dto.user.RequestUpdatePasswordDto;
import NextLevel.demo.user.dto.user.RequestUpdateUserInfoDto;
import NextLevel.demo.user.dto.user.ResponseUserInfoDto;
import NextLevel.demo.user.entity.UserEntity;
import NextLevel.demo.user.service.UserService;
import NextLevel.demo.util.jwt.JWTUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/social/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUserInfo() {
        Long userId = JWTUtil.getUserIdFromSecurityContext();

        UserEntity user = userService.getUserInfo(userId);

        ResponseUserInfoDto dto = ResponseUserInfoDto.of(user);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success", dto));
    }

    @PutMapping
    public ResponseEntity<?> updateUserInfo(@RequestBody @Valid RequestUpdateUserInfoDto dto) {
        Long userId = JWTUtil.getUserIdFromSecurityContext();
        dto.setId(userId);

        userService.updateUserInfo(dto);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success", null));
    }

    @PutMapping("/img")
    public ResponseEntity<?> updateUserImg(@RequestParam @NotEmpty MultipartFile img) {
        Long userId = JWTUtil.getUserIdFromSecurityContext();
        userService.updateUserImg(userId, img);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success", null));
    }

    // password 변경
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody @Valid RequestUpdatePasswordDto dto) {
        dto.setUserId(JWTUtil.getUserIdFromSecurityContext());
        userService.updateUserPassword(dto);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success", null));
    }

}
