package NextLevel.demo.funding.service;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.funding.dto.FundingDto;
import NextLevel.demo.funding.entity.FundingEntity;
import NextLevel.demo.funding.entity.OptionEntity;
import NextLevel.demo.funding.repository.FundingRepository;
import NextLevel.demo.funding.repository.OptionRepository;
import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.user.entity.UserEntity;
import NextLevel.demo.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FundingService {

    private final UserRepository userRepository;
    private final FundingRepository fundingRepository;
    private final EntityManager entityManager;
    private final OptionRepository optionRepository;

    @Transactional
    public void funding(FundingDto dto) {
        // user 값을 모든 funding함수를 부르는 곳에서 가지고 있어야 한다면 -> 나중에 변경 필요
        UserEntity user = userRepository.findById(dto.getUserId()).orElseThrow(
            ()->{throw new CustomException(ErrorCode.SIBAL_WHAT_IS_IT, "FundingService::funding :: can no find User");}
        );
        dto.setUser(user);

        OptionEntity option = optionRepository.findById(dto.getOptionId()).orElseThrow(
            () -> {throw new CustomException(ErrorCode.NOT_FOUND_OPTION);}
        );
        dto.setOption(option);
        dto.setOptionPrice(option.getPrice());
        dto.setFreePrice(option.getPrice() < dto.getTotalPrice() ? dto.getTotalPrice()-option.getPrice() : 0);

        dto.setProject(entityManager.getReference(ProjectEntity.class, dto.getProjectId()));

        if(user.getPoint() < dto.getTotalPrice())
            throw new CustomException(ErrorCode.NOT_ENOUGH_POINT, String.valueOf(user.getPoint()), String.valueOf(dto.getTotalPrice()));

        // user 정보 update
        userRepository.minusPointByUserId(dto.getTotalPrice(), dto.getUserId());

        // save new finding
        fundingRepository.save(dto.toEntity());
    }

}
