package NextLevel.demo.funding.dto.response;

import NextLevel.demo.funding.entity.OptionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OptionResponseDto {
    private Long id;
    private Integer price;
    private String description = "free";

    public static OptionResponseDto of(OptionEntity entity) {
        OptionResponseDto dto = new OptionResponseDto();
        dto.setId(entity.getId());
        dto.setPrice(entity.getPrice());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}
