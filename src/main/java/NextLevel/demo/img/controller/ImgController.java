package NextLevel.demo.img.controller;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
public class ImgController {

    @Value("${IMG_PATH}")
    private String IMG_PATH;

    @GetMapping("/{imgPath}")
    public ResponseEntity<Resource> getImg(@PathVariable("imgPath") String imgPath) {
        Path path = Paths.get(System.getProperty("user.dir") ,IMG_PATH, imgPath);
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e){
            e.printStackTrace();
            throw new CustomException(ErrorCode.WRONG_IMG_PATH, e.getMessage());
        }

        if(!resource.exists())
            throw new CustomException(ErrorCode.NOT_FOUND_IMG, imgPath);

        return ResponseEntity
            .ok()
            // .contentType(MediaType.IMAGE_PNG)
            .body(resource);
    }
}
