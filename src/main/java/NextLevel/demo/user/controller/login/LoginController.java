package NextLevel.demo.user.controller.login;

import NextLevel.demo.common.SuccessResponse;
import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.img.service.ImgService;
import NextLevel.demo.user.dto.RequestUserCreateDto;
import NextLevel.demo.user.dto.login.RequestUserLoginDto;
import NextLevel.demo.user.entity.UserDetailEntity;
import NextLevel.demo.user.service.LoginService;
import NextLevel.demo.util.jwt.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/public/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final JWTUtil jwtUtil;
    private final ImgService imgService;

    @PutMapping
    public ResponseEntity<?> register(
        @ModelAttribute @Valid RequestUserCreateDto requestUserCreateDto,
        HttpServletResponse httpServletResponse) {

        loginService.checkEmailIsNotExist(requestUserCreateDto.getEmail());

        // save img get uri
        ImgEntity savedImg = imgService.saveImg(requestUserCreateDto.getImg());
        requestUserCreateDto.setImgEntity(savedImg);

        UserDetailEntity createdUser = loginService.register(requestUserCreateDto);

        jwtUtil.addRefresh(httpServletResponse, createdUser.getId(), createdUser.getUUID());

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("success", null));
    }

    @PostMapping
    public ResponseEntity<?> login(
        @RequestBody @Valid RequestUserLoginDto requestUserLoginDto,
        HttpServletResponse httpServletResponse) {

        UserDetailEntity user = loginService.login(requestUserLoginDto);

        jwtUtil.addRefresh(httpServletResponse, user.getId(), user.getUUID());

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("success", null));
    }
}
