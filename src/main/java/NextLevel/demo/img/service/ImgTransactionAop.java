package NextLevel.demo.img.service;

import java.nio.file.Path;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ImgTransactionAop {
    private final ImgServiceImpl imgService;

    @Around("@annotation(imgTransaction)")
    public Object around(ProceedingJoinPoint joinPoint, ImgTransaction imgTransaction) throws Throwable {
        ArrayList<Path> imgPaths = new ArrayList<>();
        log.info("img transaction start");
        try{
            Object[] args = joinPoint.getArgs();
            args[args.length-1] = imgPaths;
            return joinPoint.proceed(args);
        } catch (Exception e){
            log.info("img transaction :: error :: roll back all img files");
            imgService.deleteImgFile(imgPaths);
            throw e;
        }
    }
}
