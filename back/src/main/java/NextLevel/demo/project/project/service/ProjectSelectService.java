//package NextLevel.demo.project.project.service;
//
//import NextLevel.demo.project.project.dto.request.RequestMainPageProjectListDto;
//import NextLevel.demo.project.project.dto.response.ResponseProjectListDetailDto;
//import NextLevel.demo.project.project.dto.response.ResponseProjectListDto;
//import NextLevel.demo.project.project.entity.ProjectEntity;
//import NextLevel.demo.project.project.repository.ProjectRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class ProjectSelectService {
//
//    private final ProjectRepository projectRepository;
//
//    public ResponseProjectListDto getAllProjects(List<ResponseProjectListDetailDto> detailDtos, RequestMainPageProjectListDto dto) {
//        List<ResponseProjectListDetailDto> detailDtos
//
//        // set tags -> project select service 를 따로 분리해서 두는 것이 합당하다고 판단함
//        Map<Long, ResponseProjectListDetailDto> dtoMap = new HashMap<>();
//        detailDtos.forEach(e -> {dtoMap.put(e.getId(), e);});
//
//        List<ProjectEntity> tags = projectRepository.findTagsByIds(dtoMap.keySet());
//        tags.forEach((e)->
//                dtoMap.get(e.getId()).setTags(
//                        e.getTags().stream().map(pt->pt.getTag().getName()).toList()
//                )
//        );
//
//        ResponseProjectListDto resultDto = new ResponseProjectListDto(detailDtos, dto.getPageCount(), dto.getPage());
//
//        return resultDto;
//    }
//
//}
