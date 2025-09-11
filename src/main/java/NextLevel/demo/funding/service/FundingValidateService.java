package NextLevel.demo.funding.service;

import NextLevel.demo.funding.repository.FreeFundingRepository;
import NextLevel.demo.funding.repository.OptionFundingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FundingValidateService {
    private final OptionFundingRepository optionFundingRepository;
    private final FreeFundingRepository freeFundingRepository;

    // validate not exist funding (Option option) // return boolean or return void and throw?
    // validate not exist funding (Project project) // db 조회인데 구지 option validate service에 둘 이유가 없어 보임

    public Long getTotalFundingPrice(Long projectId) {
        return optionFundingRepository.getTotalPriceByProject(projectId) +
                freeFundingRepository.getTotalPriceByProject(projectId);
    }

}
