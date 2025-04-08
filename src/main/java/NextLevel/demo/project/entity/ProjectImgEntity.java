package NextLevel.demo.project.entity;

import NextLevel.demo.img.entity.ImgEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "project_img", uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "img_id"}))
@Getter
@NoArgsConstructor
public class ProjectImgEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = ProjectEntity.class)
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @ManyToOne(targetEntity = ImgEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "img_id")
    private ImgEntity img;

    @Builder
    public ProjectImgEntity(ProjectEntity project, ImgEntity img) {
        this.project = project;
        this.img = img;
    }

    @Override
    public String toString() {
        return "ProjectImgEntity{" +
            "id=" + id +
            ", img=" + img +
            '}';
    }
}
