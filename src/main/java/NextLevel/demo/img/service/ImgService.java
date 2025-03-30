package NextLevel.demo.img.service;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.img.repository.ImgRepository;
import jakarta.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImgService {

    @Value("${img.default_dir}")
    private String IMG_DEFAULT_PATH;

    @Value("${img.max_len}")
    private int MAX_IMG_LEN;

    private final ServletContext servletContext;
    private final ImgRepository imgRepository;

    public ImgEntity saveImg(MultipartFile imgFile) {
        try {
            byte[] bytes = imgFile.getBytes();
            String fileName = imgFile.getOriginalFilename();

            fileName = addImgNumber(fileName);

            Path path = Paths.get(System.getProperty("user.dir") ,IMG_DEFAULT_PATH, fileName);

            Files.write(path, bytes);

            ImgEntity saved = imgRepository.save(new ImgEntity(fileName));

            log.info("Saved image :" + saved.getUri());

            return saved;
        }catch (Exception e){
            log.error(e.getMessage());
            log.info("save img fail");
            throw new CustomException(ErrorCode.ERROR_ON_SAVE_IMG);
        }
    }

    public ImgEntity updateImg(MultipartFile imgFile, ImgEntity oldImg) {
        deleteImg(oldImg);
        return saveImg(imgFile);
    }

    public void deleteImg(ImgEntity img) {
        try {
            Files.delete(Paths.get(System.getProperty("user.dir") ,IMG_DEFAULT_PATH, img.getUri()));
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new CustomException(ErrorCode.ERROR_ON_DELETE_IMG);
        }
        imgRepository.delete(img);
    }

    public ImgEntity saveSocialImg(String imgURL) {
        if (imgURL == null || imgURL.isEmpty())
            return null;

        try {
            BufferedImage image = ImageIO.read(new URL(imgURL));

            if(image == null) {
                log.info("no supported img file");
                return null;
            }

            File file = new File(System.getProperty("user.dir") + IMG_DEFAULT_PATH + addImgNumber(imgURL));
            file.createNewFile();

            ImageIO.write(image, imgURL.substring(imgURL.indexOf('.') + 1), file);

            ImgEntity saved = imgRepository.save(new ImgEntity(file.getName()));

            return saved;
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new CustomException(ErrorCode.ERROR_ON_SAVE_IMG);
        }
    }

    private String addImgNumber(String imgUri) {
        if(imgUri.lastIndexOf('/') != -1)
            imgUri = imgUri.substring(imgUri.lastIndexOf('/')+1);

        if(imgUri.length() > MAX_IMG_LEN)
            imgUri = imgUri.substring(imgUri.length()-MAX_IMG_LEN, imgUri.length());

        int lastIndex = imgUri.lastIndexOf(".");
        String extension = null, imgName = null;
        if(lastIndex == -1) {
            extension = ".img";
            imgName = imgUri;
        }else{
            extension = imgUri.substring(imgUri.lastIndexOf('.'));
            imgName = imgUri.substring(0, imgUri.lastIndexOf('.') -1 );
        }

        List<ImgEntity> savedImgs = imgRepository.findContainImgUri(imgName);

        return imgName + savedImgs.size() + extension;
    }
}
