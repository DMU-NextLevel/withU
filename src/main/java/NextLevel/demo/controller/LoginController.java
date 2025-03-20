package NextLevel.demo.controller;

import NextLevel.demo.dto.UserDto.RequestUserCreateDto;
import NextLevel.demo.dto.UserDto.RequestUserLoginDto;
import NextLevel.demo.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/login")
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @PutMapping
    public ResponseEntity<?> register(@RequestBody RequestUserCreateDto requestUserCreateDto, HttpServletResponse httpServletResponse) {
        userService.register(requestUserCreateDto, httpServletResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody RequestUserLoginDto requestUserLogin, HttpServletResponse httpServletResponse) {
        userService.login(requestUserLogin, httpServletResponse);

        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
