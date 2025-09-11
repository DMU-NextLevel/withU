package NextLevel.demo.option;

import NextLevel.demo.exception.CustomException;
import NextLevel.demo.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptionValidateService {

    private final OptionRepository optionRepository;

    public OptionEntity getOption(Long optionId){
        return optionRepository.findById(optionId).orElseThrow(
                ()->{throw new CustomException(ErrorCode.NOT_FOUND, "option");}
        );
    }

}
