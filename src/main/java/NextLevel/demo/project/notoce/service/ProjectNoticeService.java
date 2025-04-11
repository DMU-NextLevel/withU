package NextLevel.demo.project.notoce.service;

import NextLevel.demo.img.service.ImgService;
import NextLevel.demo.project.notoce.dto.SaveProjectNoticeRequestDto;
import NextLevel.demo.project.notoce.repository.ProjectNoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectNoticeService {

    private final ProjectNoticeRepository projectNoticeRepository;
    private final ImgService imgService;

    public void saveProjectNotice(SaveProjectNoticeRequestDto dto) {
        if(dto.getNoticeId() != null && dto.getImg() == null){
            // 이전 사진값을 다시 가져옴
            dto.setImgEntity(projectNoticeRepository.findById(dto.getNoticeId()).orElseThrow().getImg());
        }
        if(dto.getNoticeId() == null && dto.getImg() != null){
            dto.setImgEntity(imgService.saveImg(dto.getImg()));
        }

        projectNoticeRepository.save(dto.toEntity());
    }
    public void deleteProjectNotice(Long id) {
        projectNoticeRepository.deleteById(id);
    }

}
