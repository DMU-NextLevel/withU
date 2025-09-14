package NextLevel.demo.img.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "img_count")
@NoArgsConstructor
public class ImgCountEntity {
    @Id
    @Column
    private Long id; // to 99999
}
