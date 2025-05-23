package NextLevel.demo.funding.service;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.funding.dto.request.RequestFundingDto;
import NextLevel.demo.funding.entity.FundingEntity;
import NextLevel.demo.funding.entity.OptionEntity;
import NextLevel.demo.funding.repository.FundingRepository;
import NextLevel.demo.funding.repository.OptionRepository;
import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.project.project.repository.ProjectRepository;
import NextLevel.demo.user.entity.UserEntity;
import NextLevel.demo.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
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
    private final OptionRepository optionRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public void funding(RequestFundingDto dto) {
        UserEntity user = userRepository.findById(dto.getUserId()).orElseThrow(
            ()->{throw new CustomException(ErrorCode.ACCESS_TOKEN_ERROR);}
        );
        dto.setUser(user);
        dto.setProject(projectRepository.findByIdWithAll(dto.getProjectId()).orElseThrow(
            ()->{throw new CustomException(ErrorCode.NOT_FOUND, "project");}
        ));
        if(dto.getOptionId() != null && dto.getOptionId() > 0)
            dto.setOption(optionRepository.findById(dto.getOptionId()).orElse(null));

        // user 금액 먼저 확인
        if(user.getPoint() < dto.getPrice())
            throw new CustomException(ErrorCode.NOT_ENOUGH_POINT, String.valueOf(user.getPoint()), String.valueOf(dto.getPrice()));

        int totalPrice = dto.getPrice();
        int optionPrice = optionFunding(dto.getOption(), user, totalPrice, dto.getCount());
        freeFunding(dto.getProject(), user, totalPrice - optionPrice);

        // user 정보 update
        userRepository.minusPointByUserId(totalPrice, dto.getUserId());
    }

    @Transactional
    public void cancelFunding(Long fundingId, Long userId) {
        FundingEntity oldFunding = fundingRepository.findById(fundingId).orElseThrow(
            ()->{throw new CustomException(ErrorCode.NOT_FOUND, "funding");}
        );

        int cancelPrice = 0;

        if(oldFunding.getOption() == null)
            cancelPrice = oldFunding.getFreePrice();
        else
            cancelPrice = oldFunding.getOption().getPrice() * oldFunding.getCount();

        userRepository.addPointByUserId(cancelPrice, userId);
        fundingRepository.delete(oldFunding);
    }

    private int optionFunding(OptionEntity option, UserEntity user, int totalPrice, int count) {
        if(option == null)
            return 0;

        if(option.getPrice() * count > totalPrice)
            throw new CustomException(ErrorCode.NOT_ENOUGH_PRICE_OPTION, String.valueOf(option.getPrice()), String.valueOf(totalPrice));

        // 이전 주문 확인
        Optional<FundingEntity> oldFunding = fundingRepository.findByUser_IdAndOption_Id(user.getId(), option.getId());

        if(oldFunding.isEmpty())
            fundingRepository.save(FundingEntity
                .builder()
                .option(option)
                .project(option.getProject())
                .user(user)
                .freePrice(0)
                .count(count)
                .build()
            );
        else
            oldFunding.get().upCount(count);

        return option.getPrice();
    }

    private int freeFunding(ProjectEntity project, UserEntity user, int freePrice) {
        if(freePrice == 0)
            return 0;

        // 이전 주문 확인
        Optional<FundingEntity> oldFunding = fundingRepository.findByUser_IdAndProject_IdAndOption_IdIsNull(user.getId(), project.getId());

        if(oldFunding.isEmpty())
            fundingRepository.save(FundingEntity
                .builder()
                .option(null)
                .project(project)
                .user(user)
                .freePrice(freePrice)
                .build()
            );
        else
            oldFunding.get().upFreePrice(freePrice);

        return freePrice;
    }

}
