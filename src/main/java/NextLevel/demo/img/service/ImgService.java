package NextLevel.demo.img.service;

import NextLevel.demo.img.entity.ImgEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public interface ImgService {

    ImgEntity saveImg(MultipartFile imgFile, ArrayList<Path> imgPaths);
    ImgEntity updateImg(MultipartFile imgFile, ImgEntity oldImg, ArrayList<Path> imgPaths);
    void deleteImg(ImgEntity img);
    ImgEntity saveSocialImg(String imgURL);
    void deleteImgFile(List<Path> filePath) throws IOException ;

}
