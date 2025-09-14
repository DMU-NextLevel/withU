package NextLevel.demo.funding.service;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import NextLevel.demo.funding.dto.request.RequestCancelFundingDto;
import NextLevel.demo.funding.dto.request.RequestFreeFundingDto;
import NextLevel.demo.funding.dto.request.RequestOptionFundingDto;
import NextLevel.demo.funding.entity.FreeFundingEntity;
import NextLevel.demo.option.OptionEntity;
import NextLevel.demo.funding.entity.OptionFundingEntity;
import NextLevel.demo.funding.repository.FreeFundingRepository;
import NextLevel.demo.funding.repository.OptionFundingRepository;
import NextLevel.demo.option.OptionValidateService;
import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.project.project.service.ProjectValidateService;
import NextLevel.demo.user.entity.UserEntity;
import NextLevel.demo.user.service.UserValidateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FundingService {

    private final UserValidateService userValidateService;
    private final ProjectValidateService projectValidateService;
    private final OptionValidateService optionValidateService;

    private final OptionFundingRepository optionFundingRepository;
    private final FreeFundingRepository freeFundingRepository;

    public void cancelFreeFunding(RequestCancelFundingDto dto) {
        UserEntity user = userValidateService.getUserInfo(dto.getUserId());
        FreeFundingEntity funding = freeFundingRepository.findById(dto.getId()).orElseThrow(
                ()->{return new CustomException(ErrorCode.NOT_FOUND, "freeFunding");}
        );
        if(!user.getId().equals(funding.getUser().getId()))
            throw new CustomException(ErrorCode.NOT_AUTHOR);
        freeFundingRepository.deleteById(dto.getId());
    }

    public void cancelOptionFunding(RequestCancelFundingDto dto) {
        UserEntity user = userValidateService.getUserInfo(dto.getUserId());
        OptionFundingEntity funding = optionFundingRepository.findById(dto.getId()).orElseThrow(
                ()->{return new CustomException(ErrorCode.NOT_FOUND, "optionFunding");}
        );
        if(!user.getId().equals(funding.getUser().getId()))
            throw new CustomException(ErrorCode.NOT_AUTHOR);
        optionFundingRepository.deleteById(dto.getId());
    }

    @Transactional
    public void optionFunding(RequestOptionFundingDto dto) {
        UserEntity user = userValidateService.getUserInfo(dto.getUserId());
        OptionEntity option = optionValidateService.getOption(dto.getOptionId());

        // validate price <> option.price * count
        long totalPrice = option.getPrice() * dto.getCount();

        if(totalPrice > user.getPoint())
            throw new CustomException(ErrorCode.NOT_ENOUGH_POINT, String.valueOf(user.getPoint()), String.valueOf(totalPrice));

        optionFundingRepository.save(dto.toEntity(user, option));
    }

    @Transactional
    public void freeFunding(RequestFreeFundingDto dto) {
        UserEntity user = userValidateService.getUserInfo(dto.getUserId());
        ProjectEntity project = projectValidateService.getProjectEntity(dto.getProjectId());

        if(dto.getFreePrice() > user.getPoint())
            throw new CustomException(ErrorCode.NOT_ENOUGH_POINT, String.valueOf(user.getPoint()), String.valueOf(dto.getFreePrice()));

        freeFundingRepository.save(dto.toEntity(user, project));
    }

}
