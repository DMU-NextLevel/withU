package NextLevel.demo.controller;

import NextLevel.demo.dto.UserDetailRequestDto;
import NextLevel.demo.dto.UserDto.RequestUserCreateDto;
import NextLevel.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody RequestUserCreateDto requestUserCreateDto) {
        userService.login(requestUserCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
