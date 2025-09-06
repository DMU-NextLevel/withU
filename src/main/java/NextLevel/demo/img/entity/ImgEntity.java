package NextLevel.demo.img.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter @Setter
@Table(name = "img")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImgEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String uri;

    public ImgEntity(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "ImgEntity{" +
            "id=" + id +
            ", uri='" + uri + '\'' +
            '}';
    }
}
