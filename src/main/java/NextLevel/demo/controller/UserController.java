package NextLevel.demo.controller;

import NextLevel.demo.dto.UserDto.RequestUpdateUserInfoDto;
import NextLevel.demo.dto.UserDto.ResponseUserInfoDto;
import NextLevel.demo.service.UserService;
import NextLevel.demo.util.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

        ResponseUserInfoDto dto = userService.getUserInfo(userId);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping
    public ResponseEntity<?> updateUserInfo(@RequestBody RequestUpdateUserInfoDto dto, @RequestParam MultipartFile img) {
        Long userId = JWTUtil.getUserIdFromSecurityContext();
        dto.setUserId(userId);

        return null;
    }

    // password 변경

}
