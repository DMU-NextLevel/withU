package NextLevel.demo.project.service;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.img.service.ImgService;
import NextLevel.demo.project.ProjectOrderType;
import NextLevel.demo.project.dto.request.CreateProjectDto;
import NextLevel.demo.project.dto.response.ResponseProjectListDto;
import NextLevel.demo.project.entity.ProjectEntity;
import NextLevel.demo.project.entity.ProjectImgEntity;
import NextLevel.demo.project.entity.ProjectTagEntity;
import NextLevel.demo.project.entity.TagEntity;
import NextLevel.demo.project.repository.ProjectActivityRepository;
import NextLevel.demo.project.repository.ProjectImgRepository;
import NextLevel.demo.project.repository.ProjectRepository;
import NextLevel.demo.project.repository.ProjectTagRepository;
import NextLevel.demo.project.repository.TagRepository;
import NextLevel.demo.user.entity.UserEntity;
import NextLevel.demo.user.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final UserService userService;
    private final ImgService imgService;
    private final ProjectActivityRepository projectActivityRepository;

    // 추가
    @Transactional
    public void save(CreateProjectDto dto) {
        // user 처리
        UserEntity user = userService.getUserInfo(dto.getUserId());
        validateUser(user);
        dto.setUser(user);

        dto.setTitleImgEntity(imgService.saveImg(dto.getTitleImg()));

        ProjectEntity newProject = dto.toEntity();

        // tag 처리
        newProject.setTags(getTagEntitysByIds(dto.getTags())
            .stream()
            .map((t)->{
                return ProjectTagEntity
                    .builder()
                    .project(newProject)
                    .tag(t)
                    .build();
            }).toList());

        // img 처리
        List<ImgEntity> imgEntitys = new ArrayList<>();
        dto.getImgs().forEach(imgEntity -> {imgEntitys.add(imgService.saveImg(imgEntity));});

        newProject.setImgs(
            imgEntitys.stream().map((e)->{
                return ProjectImgEntity
                    .builder()
                    .project(newProject)
                    .img(e)
                    .build();
            }).toList()
        );

        projectRepository.save(newProject);
    }
    private void validateUser(UserEntity user) {
        // user 당 한달에 생성 가능한 펀딩 갯수 제한?
        // 일단 아무 일도 하지 않음 추후 수정 예정
    }

    // 수정
    @Transactional
    public void update(CreateProjectDto dto) {
        Optional<ProjectEntity> oldProjectOptional = projectRepository.findByIdWithAll(dto.getId());

        if(oldProjectOptional.isEmpty())
            throw new CustomException(ErrorCode.NOT_FOUND_PROJECT, dto.getId().toString());

        ProjectEntity oldProject = oldProjectOptional.get();

        if(oldProject.getUser().getId() != dto.getUserId())
            throw new CustomException(ErrorCode.NOT_AUTHOR);

        dto.setUser(oldProject.getUser());

        if(dto.getTitleImg() == null)
            dto.setTitleImgEntity(oldProject.getTitleImg());
        else
            dto.setTitleImgEntity(imgService.updateImg(dto.getTitleImg(), oldProject.getTitleImg()));

        ProjectEntity newProject = dto.toEntity();

        // tag 처리
        newProject.setTags(getTagEntitysByIds(dto.getTags())
            .stream()
            .map((t)->{
                return ProjectTagEntity
                    .builder()
                    .project(newProject)
                    .tag(t)
                    .build();
            })
            .toList()
        );

        // img 처리
        if(dto.getImgs() != null) {
            List<ImgEntity> imgEntitys = new ArrayList<>();
            dto.getImgs().forEach(imgEntity -> {
                imgEntitys.add(imgService.saveImg(imgEntity));
            });

            newProject.setImgs(
                imgEntitys.stream().map((e) -> {
                    return ProjectImgEntity
                        .builder()
                        .project(newProject)
                        .img(e)
                        .build();
                }).toList()
            );
        }else{
            newProject.setImgs(oldProject.getImgs());
        }

        List<ImgEntity> oldImgs = oldProject.getImgs().stream().map(pe -> pe.getImg()).toList();
        oldImgs.forEach(i->{ imgService.deleteImg(i);});

        projectRepository.save(newProject);
    }
    private List<TagEntity> getTagEntitysByIds(List<Long> tagIds) {
        List<TagEntity> tagEntitys = tagRepository.findAllById(tagIds);

        if(tagEntitys.size() != tagIds.size()) {
            throw new CustomException(ErrorCode.NOT_CORRECT_TAG_SIZE);
        }

        return tagEntitys;
    }

    // 삭제
    public void deleteProject(Long id) {
        Optional<ProjectEntity> oldProjectOptional = projectRepository.findById(id);
        if(oldProjectOptional.isEmpty())
            throw new CustomException(ErrorCode.NOT_FOUND_PROJECT, id.toString());
        ProjectEntity oldProject = oldProjectOptional.get();

        // 펀딩 금액이 남아있다면 모두 환불 처리하기

        // 다른 soft적 처리 필요한 부분 처리하기

        // img 처리

        return; // 아직 구현하지 않음 + soft delete 처리 고민중 .....
    }

    // get list
    public List<ResponseProjectListDto> getAllProjects(Long tagId, ProjectOrderType orderType, Integer page) {
        List<ResponseProjectListDto> entities = projectActivityRepository.getAll(tagId, orderType, page);

        Map<Long, ResponseProjectListDto> dtoMap = new HashMap<>();
        entities.forEach(e -> {dtoMap.put(e.getId(), e);});

        List<ProjectEntity> tags = projectRepository.findTagsByIds(dtoMap.keySet().stream().toList());
        tags.forEach((e)->
            dtoMap.get(e.getId()).setTags(
                e.getTags().stream().map(pt->pt.getTag().getName()).toList()
            )
        );

        return entities;
    }

}
