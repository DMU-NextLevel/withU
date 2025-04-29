package NextLevel.demo.user.controller.login;

import NextLevel.demo.common.SuccessResponse;
import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.img.service.ImgService;
import NextLevel.demo.user.dto.RequestUserCreateDto;
import NextLevel.demo.user.dto.login.RequestUserLoginDto;
import NextLevel.demo.user.entity.UserDetailEntity;
import NextLevel.demo.user.service.EmailService;
import NextLevel.demo.user.service.LoginService;
import NextLevel.demo.util.jwt.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final JWTUtil jwtUtil;
    private final ImgService imgService;
    private final EmailService emailService;

    @PutMapping
    public ResponseEntity<?> register(
        @ModelAttribute @Valid RequestUserCreateDto requestUserCreateDto,
        HttpServletResponse httpServletResponse) {

        UserDetailEntity createdUser = loginService.register(requestUserCreateDto);

        //jwtUtil.addRefresh(httpServletResponse, createdUser.getUser().getId(), createdUser.getUUID());

        HashMap<String, String> claims = new HashMap<>();
        claims.put("uuid", createdUser.getUUID());
        String token = jwtUtil.makeToken(createdUser.getUser().getId().toString(), claims, 999999);

        return ResponseEntity.ok().header("refresh", token).body(new SuccessResponse("success", token));
    }

    @PostMapping
    public ResponseEntity<?> login(
        @RequestBody @Valid RequestUserLoginDto requestUserLoginDto,
        HttpServletResponse httpServletResponse) {

        UserDetailEntity user = loginService.login(requestUserLoginDto);

        //jwtUtil.addRefresh(httpServletResponse, user.getUser().getId(), user.getUUID());

        HashMap<String, String> claims = new HashMap<>();
        claims.put("uuid", user.getUUID());
        String token = jwtUtil.makeToken(user.getUser().getId().toString(), claims, 999999);

        return ResponseEntity.ok().header("refresh", token).body(new SuccessResponse("success", token));
    }

    @GetMapping("/nickName")
    public ResponseEntity<?> checkNickName(@RequestParam("nickName") String nickName) {
        if(loginService.checkNickNameIsNotExist(nickName))
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("not exist", null));
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new SuccessResponse("exist", null));
    }

    @GetMapping("/email")
    public ResponseEntity<?> checkEmailIsNotExist(@RequestParam("email") String email) {
        if(loginService.checkEmailIsNotExist(email))
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("not exist", null));
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new SuccessResponse("exist", null));
    }

    @PostMapping("/email")
    public ResponseEntity<?> sendEmail(@RequestBody Map<String , String> requestMap) {
        emailService.sendEmail(requestMap.get("email"));
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success", null));
    }

    @PutMapping("/email")
    public ResponseEntity<?> checkEmail(@RequestBody Map<String , String> requestMap) {
        if(emailService.checkEmailKey(requestMap.get("email"), requestMap.get("key")))
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("correct", null));
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new SuccessResponse("not correct", null));
    }

}
