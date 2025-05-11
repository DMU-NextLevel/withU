package NextLevel.demo.funding.dto.response;

import NextLevel.demo.funding.entity.OptionEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionResponseDto {
    private Long id;
    private int price;
    private String description;

    public static OptionResponseDto of(OptionEntity entity) {
        OptionResponseDto dto = new OptionResponseDto();
        dto.setId(entity.getId());
        dto.setPrice(entity.getPrice());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}
