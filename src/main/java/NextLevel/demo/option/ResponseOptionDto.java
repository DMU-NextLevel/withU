package NextLevel.demo.option;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseOptionDto {
    private Long id;
    private Integer price;
    private String description;

    public static ResponseOptionDto of(OptionEntity entity) {
        ResponseOptionDto dto = new ResponseOptionDto();
        dto.id =entity.getId();
        dto.price = entity.getPrice();
        dto.description = entity.getDescription();
        return dto;
    }
}
